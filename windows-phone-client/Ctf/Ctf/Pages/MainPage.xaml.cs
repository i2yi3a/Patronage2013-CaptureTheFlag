using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using Ctf.Resources;
using System.Diagnostics;


namespace Ctf
{
    public partial class MainPage : PhoneApplicationPage
    {
        public MainPage()
        {
            InitializeComponent();
            loginButton.IsEnabled = false;


            ApplicationSettings.Instance.UserChanged += UserHasChanged;
        }

        public void UserHasChanged(object sender, EventArgs e)
        {
            Debug.WriteLine("AppSett EVENT!. User has changed.");
        }

        public void SomeMessage(object sender, EventArgs e)
        {
            Debug.WriteLine("MESSAGE.");
        }

        private void txtChanged(object sender, RoutedEventArgs e)
        {

            if (usernameBox.Text.Length > 4 && passwordBox.Password.Length > 4)
            {
                loginButton.IsEnabled = true;
            }
            else
            {
                loginButton.IsEnabled = false;
            }
        }



        private void LogIn(object sender, RoutedEventArgs e)
        {
            Login Logger = new Login();
            Logger.LogInAs(new UserCredentials(usernameBox.Text, passwordBox.Password), "secret");
            Logger.MessengerSent += Logger_MessengerSent;
            
        }


        void Logger_MessengerSent(object sender, EventArgs e)
        {
            MessengerSentEventArgs x;
            x = (MessengerSentEventArgs)e;
            MessageBox.Show(x.message.ToString(), x.errorCode.ToString(), MessageBoxButton.OK);
        }
    }
}