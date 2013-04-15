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
        // Constructor
        public MainPage()
        {
            InitializeComponent();

            // Sample code to localize the ApplicationBar
            //BuildLocalizedApplicationBar();
        }

        private async void LoginTest(object sender, RoutedEventArgs e)
        {
            Login Logger = new Login();
            Debug.WriteLine(Logger.loggedAs());

            //await Logger.logInAs(new UserCredentials("piotrekm44@o2.pl", "weakPassword"), "secret");
            await Logger.logInAs(new UserCredentials("abcdef", "abcdef"), "secret");
            //TODO: NullPointerException
            //if(Logger.loggedAs().username != null)
            //    Debug.WriteLine(Logger.loggedAs().username);
        }

        private async void RegisterTest(object sender, RoutedEventArgs e)
        {
            Registration Register = new Registration();
            await Register.register(new UserCredentials("abcdef", "abcdef"), "abcdef");
        }

        private void LogoutTest(object sender, RoutedEventArgs e)
        {
            Login Logger = new Login();
            if (Logger.logOut())
                Debug.WriteLine("Logout SUCCESSFUL");
            else
                Debug.WriteLine("Logout FAILED");
        }


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


        private void makeTestUserCredentials()
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
                Debug.WriteLine("username: " + user.getUsername());
                Debug.WriteLine("password: " + user.getPassword());
            }
            if (user.hasMatchingPassword(password))
                Debug.WriteLine("match: " + user.getPassword() + " == " + password);
            if (user.hasMatchingPassword(username))
                Debug.WriteLine("match: " + user.getPassword() + " == " + username);
        }


    }
}