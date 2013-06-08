using Ctf.Models.DataObjects;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;

namespace Ctf.ApplicationTools
{
    public class Filterer
    {
        public BackgroundWorker bw = new BackgroundWorker();
        private int total = 200000000;
        private int fraction = 2000000;
        static int count = 0;
        private int mycount;

        public Filterer()
        {
            mycount = 0;
            bw.WorkerReportsProgress = true;
            bw.WorkerSupportsCancellation = true;
            bw.DoWork += new DoWorkEventHandler(bw_DoWork);
            bw.ProgressChanged += new ProgressChangedEventHandler(bw_ProgressChanged);
            bw.RunWorkerCompleted += new RunWorkerCompletedEventHandler(bw_RunWorkerCompleted);
        }

        private void bw_DoWork(object sender, DoWorkEventArgs e)
        {
            count++;
            mycount = count;
            BackgroundWorker worker = sender as BackgroundWorker;

            for (int i = 0; i < total; i++)
            {
                if ((worker.CancellationPending == true))
                {
                    e.Cancel = true;
                    break;
                }
                else
                {
                    // Perform a time consuming operation and report progress.
                    if (i % (fraction*10) == 0)
                        worker.ReportProgress(i / fraction);
                }
            }
        }
        private void bw_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            if (e.Cancelled == true)
            {
                System.Diagnostics.Debug.WriteLine("[" + mycount + "]" + "Canceled!");
            }
            else if (!(e.Error == null))
            {
                System.Diagnostics.Debug.WriteLine("[" + mycount + "]" + "Error: " + e.Error.Message);
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("[" + mycount + "]" + "Done!");
            }
        }

        private void bw_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("[" + mycount + "]" + e.ProgressPercentage.ToString() + " / " + (total / fraction).ToString());
        }














        public static CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        public static bool isRunning = false;
        public static ObservableCollection<GameHeader> Filter(ObservableCollection<GameHeader> source, string name, CancellationToken cancellationToken)
        {
            if (Filterer.isRunning)
                Filterer.cancellationTokenSource.Cancel();
            System.Diagnostics.Debug.WriteLine("Normal Filter START for name: " + name);
            ObservableCollection<GameHeader> destination = new ObservableCollection<GameHeader>();
            for (int i = 0; i < int.MaxValue / 8; i++)
            {
                if ((i % (int.MaxValue / 100)) == 0)
                    System.Diagnostics.Debug.WriteLine("Working...");
                if (cancellationToken.IsCancellationRequested)
                {
                    isRunning = false;
                    System.Diagnostics.Debug.WriteLine("Canceled");
                    return source;
                }
            }

            foreach (GameHeader g in source)
            {
                if (g.Name != null)
                {
                    Match match = Regex.Match(g.Name, name, RegexOptions.IgnoreCase);
                    if (match.Success)
                    {
                        //System.Diagnostics.Debug.WriteLine("For name: " + g.Name + " |regex: " + name);
                        //foreach (Group oneMatch in match.Groups)
                        //{
                        //    string key = oneMatch.Value;
                        //    System.Diagnostics.Debug.WriteLine("Regex match key: " + key);
                        //}
                        destination.Add(g);
                    }
                }
            }
            //MyGamesListControl.ItemsSource = destination;
            System.Diagnostics.Debug.WriteLine("Normal Filter STOP." + name);
            isRunning = false;
            return destination;
        }

        public static Task<ObservableCollection<GameHeader>> FilterAsyncCritical(ObservableCollection<GameHeader> source, string name, CancellationToken cancellationToken)
        {
            cancellationTokenSource = new CancellationTokenSource();
            System.Diagnostics.Debug.WriteLine("Inside ASYNC." + name);

            return Task.Run(() => Filter(source, name, cancellationToken), cancellationToken);
        }

    }
}
