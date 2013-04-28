using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using System.Diagnostics;
using System.Threading;

using System.Windows.Controls.Primitives;
using System.ComponentModel;


namespace Ctf
{
    public partial class LoggedIn : PhoneApplicationPage
    {
        private Popup popup;
        private BackgroundWorker backroungWorker;

        private void ShowSplash()
        {
            this.popup = new Popup();
            this.popup.Child = new LoadingScreen();
            this.popup.IsOpen = true;
            StartLoadingData();
        }

        public LoggedIn()
        {                  
            InitializeComponent();
            //ApplicationSettings.Instance.UserChanged += UserHasChanged;
        }

        protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
         
            base.OnNavigatedTo(e);
            ShowSplash();


        }

        private void Logout(object sender, RoutedEventArgs e)
        {
            Login Logger = new Login();
            if (Logger.LogOut())
                Debug.WriteLine("Logout SUCCESSFUL");
            else
                Debug.WriteLine("Logout FAILED");
         NavigationService.GoBack();
        }

        //public void UserHasChanged(object sender, EventArgs e)
        //{
            
        //    this.popup.IsOpen = false;
        //    welcomeBlock.Text = ApplicationSettings.Instance.RetriveLoggedUser().username;
        //}

        private void StartLoadingData()
        {
            backroungWorker = new BackgroundWorker();
            backroungWorker.DoWork += new DoWorkEventHandler(backroungWorker_DoWork);
            backroungWorker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(backroungWorker_RunWorkerCompleted);
            backroungWorker.RunWorkerAsync();
        }

        void backroungWorker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            this.Dispatcher.BeginInvoke(() =>
            {
                this.popup.IsOpen = false;
                if (ApplicationSettings.Instance.RetriveLoggedUser().access_token != String.Empty)
                {
                    welcomeBlock.Text = ApplicationSettings.Instance.RetriveLoggedUser().username;
                    
                }
                else
                {
                    MessageBox.Show("Podano błędny login lub hasło", "Niezalogowano", MessageBoxButton.OK);
                    NavigationService.GoBack();
                }
            }
            );
        }

        void backroungWorker_DoWork(object sender, DoWorkEventArgs e)
        {
            //here we can load data
            Thread.Sleep(1000);
        }


        
    }
}