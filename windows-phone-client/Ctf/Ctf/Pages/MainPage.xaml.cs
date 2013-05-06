﻿using System;
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
        // Constructor
        public MainPage()
        {
            InitializeComponent();
            loginButton.IsEnabled= false;
            

            // Sample code to localize the ApplicationBar
            //BuildLocalizedApplicationBar();
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

            if (UserCredentials.IsUsernameProperLength(usernameBox.Text) && UserCredentials.IsPasswordProperLength(passwordBox.Password)){
                loginButton.IsEnabled = true;
            }
            else{
                loginButton.IsEnabled = false;}
            }
        


        private async void LogIn(object sender, RoutedEventArgs e)
        {
            Login Logger = new Login();
              await Logger.LogInAs(new UserCredentials(usernameBox.Text, passwordBox.Password), "secret");
            //Logger.MessengerSent += Logger_MessengerSent;
            NavigationService.Navigate(new Uri("/Pages/LoggedIn.xaml?", UriKind.Relative));   
        }

        //void Logger_MessengerSent(object sender, EventArgs e)
        //{
        //    MessengerSentEventArgs x;
        //    x = (MessengerSentEventArgs)e;
        //    MessageBox.Show(x.message.ToString(), x.errorCode.ToString(), MessageBoxButton.OK);
        //    Debug.WriteLine(x.errorCode.ToString());
        //    if (ApplicationSettings.Instance.RetriveLoggedUser().access_token == String.Empty)
        //    {
        //        NavigationService.Navigate(new Uri("/Pages/LoggedIn.xaml?", UriKind.Relative));
        //    }


        //}

        // Sample code for building a localized ApplicationBar
        //private void BuildLocalizedApplicationBar()
        //{
        //    // Set the page's ApplicationBar to a new instance of ApplicationBar.
        //    ApplicationBar = new ApplicationBar();

        //    // Create a new button and set the text value to the localized string from AppResources.
        //    ApplicationBarIconButton appBarButton = new ApplicationBarIconButton(new Uri("/Assets/AppBar/appbar.add.rest.png", UriKind.Relative));
        //    appBarButton.Text = AppResources.AppBarButtonText;
        //    ApplicationBar.Buttons.Add(appBarButton);

        //    // Create a new menu item with the localized string from AppResources.
        //    ApplicationBarMenuItem appBarMenuItem = new ApplicationBarMenuItem(AppResources.AppBarMenuItemText);
        //    ApplicationBar.MenuItems.Add(appBarMenuItem);
        //}

        private void MakeTestUserCredentials()
        {
            UserCredentials user = null;
            string username = "aaaaaaaa";
            string password = "bbbbb";
            try
            {
                user = new UserCredentials(username, password);
            }
            catch (Exception ex)
            {
                Debug.WriteLine("Exception: " + ex.Message);
            }
            if (user != null)
            {
                Debug.WriteLine("username: " + user.GetUsername());
                Debug.WriteLine("password: " + user.GetPassword());
            }
            if (user.HasMatchingPassword(password))
                Debug.WriteLine("match: " + user.GetPassword() + " == " + password);
            if (user.HasMatchingPassword(username))
                Debug.WriteLine("match: " + user.GetPassword() + " == " + username);
        }
    }
}