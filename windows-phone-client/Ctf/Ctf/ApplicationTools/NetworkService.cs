using Ctf.Communication.DataObjects;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Phone.Net.NetworkInformation;

namespace Ctf.ApplicationTools
{
    //TODO: Write NetworkService and test when device is avalible
    public class NetworkService
    {
        public static List<Task<RestRequestAsyncHandle>> RestRequestTasks;
        volatile static bool cancel;
        static int delay = 300;
        static volatile int disableNetworkCount;

        static NetworkService()
        {
            RestRequestTasks = new List<Task<RestRequestAsyncHandle>>();
            cancel = false;
            disableNetworkCount = 0;
        }

        ////Preferably this method
        //public static bool IsNetworkEnabled()
        //{
        //    return (Microsoft.Phone.Net.NetworkInformation.NetworkInterface.NetworkInterfaceType !=
        //        Microsoft.Phone.Net.NetworkInformation.NetworkInterfaceType.None);
        //}

        //Preferably this method
        public static bool IsNetworkEnabled()
        {
            return DeviceNetworkInformation.IsWiFiEnabled || DeviceNetworkInformation.IsCellularDataEnabled;
        }

         

        //Preferably this method
        public static Task<bool> IsNetworkEnabledAsync()
        {
            return Task.Run(() => IsNetworkEnabled());
        }

        //Preferably this method
        public static void RequestFinished_Event(object sender, RequestFinishedEventArgs e)
        {
            cancel = true;
        }

        //Preferably this method
        public static async Task AbortIfNoNetworkAsync(RestRequestAsyncHandle requestHandle)
        {
            bool networkEnabled;
            while (!cancel)
            {
                Debug.WriteLine("IsNetworkEnabled(): " + IsNetworkEnabled());
                networkEnabled = await IsNetworkEnabledAsync();
                //if (disableNetworkCount >= 3)
                //    networkEnabled = false;
                Debug.WriteLine("networkEnabled: " + networkEnabled);
                if (!networkEnabled)
                {
                    requestHandle.Abort();
                    Debug.WriteLine("requestHandle.Abort()");
                    return;
                }
                await Task.Delay(delay);
                disableNetworkCount++;
            }
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
