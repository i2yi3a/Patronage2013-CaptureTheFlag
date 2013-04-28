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


namespace Ctf
{
    public partial class LoggedIn : PhoneApplicationPage
    {
        private Popup popup;

        private void ShowSplash()
        {
            this.popup = new Popup();
            this.popup.Child = new LoadingScreen();
            this.popup.IsOpen = true;
            // StartLoadingData();
        }

        public LoggedIn()
        {   
            InitializeComponent();
            
            ApplicationSettings.Instance.UserChanged += UserHasChanged;
        }

        protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
         
            base.OnNavigatedTo(e);
            ShowSplash();
            
            //Thread.Sleep(10000);
            
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

        public void UserHasChanged(object sender, EventArgs e)
        {
            welcomeBlock.Text = ApplicationSettings.Instance.RetriveLoggedUser().username;
            this.popup.IsOpen = false;

        }

       
    }
}