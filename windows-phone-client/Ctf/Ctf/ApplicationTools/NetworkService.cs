using RestSharp;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Ctf.ApplicationTools
{
    public class NetworkService
    {
        public static List<Task<RestRequestAsyncHandle>> RestRequestTasks;

        static NetworkService()
        {
            RestRequestTasks = new List<Task<RestRequestAsyncHandle>>();
        }

        //Preferably this method
        public static bool IsNetworkEnabled()
        {
            return (Microsoft.Phone.Net.NetworkInformation.NetworkInterface.NetworkInterfaceType !=
                Microsoft.Phone.Net.NetworkInformation.NetworkInterfaceType.None);
        }

        //Preferably this method
        public static Task<bool> IsNetworkEnabledAsync()
        {
            return Task.Run(() => IsNetworkEnabled());
        }

        public static void DoInBackground()
        {
            Thread worker = new Thread(NetworkService.Work);
            worker.IsBackground = true;
            worker.Start();
        }

        public static void Work()
        {
            while (Thread.CurrentThread.IsAlive)
            {
                Debug.WriteLine("Is internet on: " + IsNetworkEnabled().ToString());
                Thread.Sleep(50);
            }
        }

        //Preferably this method
        public static async Task WorkAsync()
        {
            while (true)
            {
                Debug.WriteLine("Is internet on: " + await IsNetworkEnabledAsync());
                await Task.Delay(50);
            }
        }

        public static async Task OtherAsync()
        {
            Debug.WriteLine("Inside async Task OtherAsync()");
            for(int i = 0; i < 10; i++)
            {
                await Task.Delay(5000);
                Debug.WriteLine("Crunching other task...");
            }
            Debug.WriteLine("Exiting inside of async Task OtherAsync()");
        }

        public static void AddTask(Task<RestRequestAsyncHandle> restRequestTask)
        {
            RestRequestTasks.Add(restRequestTask);
        }

        public void RemoveTask(Task<RestRequestAsyncHandle> restRequestTask)
        {
            RestRequestTasks.Remove(restRequestTask);
        }

        //public bool IsTaskRunning()
        //{

        //}

        //public static void AbortTasks()
        //{
        //    foreach (Task<RestRequestAsyncHandle> task in RestRequestTasks)
        //    {
        //        System.Threading.Tasks.TaskStatus.
        //        if(task.Status
        //        task.Result.Abort()
        //    }
        //}
    }
}
