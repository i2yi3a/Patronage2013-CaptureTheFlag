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

namespace Ctf
{
    public partial class LoggedIn : PhoneApplicationPage
    {
        public LoggedIn()
        {
            InitializeComponent();
        }

        protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
         
            base.OnNavigatedTo(e);
            //welcomeBlock.Text = "Jesteś zalogowany jako " + NavigationContext.QueryString["text"];
            welcomeBlock.Text = ApplicationSettings.Instance.RetriveLoggedUser().username;
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

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            welcomeBlock.Text = ApplicationSettings.Instance.RetriveLoggedUser().username;
        }


    }
}