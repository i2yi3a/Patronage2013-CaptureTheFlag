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
            ApplicationSettings s = ApplicationSettings.Instance;
            //s.clearSettings();
            LoginResponse r = s.RetriveLoginSession();
            
            Debug.WriteLine("Retrieved from ApplicationSettings:");
            if (r != null)
            {
                Debug.WriteLine("response.Data.access_token: " + r.access_token);
                Debug.WriteLine("response.Data.token_type: " + r.token_type);
                Debug.WriteLine("response.Data.scope: " + r.scope);

                Debug.WriteLine("response.Data.error_code: " + r.error);
                Debug.WriteLine("response.Data.error_code: " + r.error_description);
            }
            Login logNow = new Login();
            logNow.makeRequest(logNow.createRequest());



            // Sample code to localize the ApplicationBar
            //BuildLocalizedApplicationBar();
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
    }
}