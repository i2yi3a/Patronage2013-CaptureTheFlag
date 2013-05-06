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
        public LoggedIn()
        {
            InitializeComponent();

            ApplicationSettings.Instance.UserChanged += UserHasChanged;
        }

        protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            
        }

        private void Logout(object sender, RoutedEventArgs e)
        {
            Login Logger = new Login();
            if (Logger.LogOut())
                Debug.WriteLine("Logout SUCCESSFUL");
            else
                Debug.WriteLine("Logout FAILED");

            if (NavigationService.CanGoBack)
            {
                NavigationService.GoBack();
            }
            else { NavigationService.Navigate(new Uri("/Pages/MainPage.xaml?", UriKind.Relative)); }
        }

        public void UserHasChanged(object sender, EventArgs e)
        {
            welcomeBlock.Text = ApplicationSettings.Instance.RetriveLoggedUser().username;
        }


        private void PhoneApplicaitonPage_BackKeyPress(object sender, System.ComponentModel.CancelEventArgs e)
        {
            e.Cancel = true;
        }

    }
}