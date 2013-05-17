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
using Ctf.Communication;
using System.Diagnostics;
using Ctf.Communication.DataObjects;
using Ctf.ApplicationTools.DataObjects;
using Ctf.ApplicationTools;

namespace Ctf.Pages
{
    public partial class MainPage : PhoneApplicationPage
    {
        public MainPage()
        {
            InitializeComponent();
            loginButton.IsEnabled = false;
            registerButton.IsEnabled = false;

            ApplicationSettings.Instance.UserChanged += UserHasChanged;
        }

        protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
            if (!ApplicationSettings.Instance.HasLoginInfo())
            {
                if (NavigationService.CanGoBack)
                {
                    while (NavigationService.CanGoBack) NavigationService.RemoveBackEntry();
                }
            }
            base.OnNavigatedTo(e);
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

        private void txtChangedRegister(object sender, RoutedEventArgs e)
        {
            if (userNameRegister.Text.Length >= UserCredentials.MINIMAL_USERNAME_LENGTH &&
                passwordRegister1.Password.Length >= UserCredentials.MINIMAL_PASSWORD_LENGTH &&
                passwordRegister2.Password.Length >= UserCredentials.MINIMAL_PASSWORD_LENGTH &&
                passwordRegister1.Password.Equals(passwordRegister2.Password))
            {
                registerButton.IsEnabled = true;
            }
            else
            {
                registerButton.IsEnabled = false;
            }
        }

        private void LogIn(object sender, RoutedEventArgs e)
        {
            waitIndicator.Visibility = Visibility.Visible;
            LoginCommand Logger = new LoginCommand();
            Logger.RequestFinished += new RequestFinishedEventHandler(Logger_MessengerSent);
            Logger.LogInAs(new UserCredentials(usernameBox.Text, passwordBox.Password), "secret");
            usernameBox.Text = String.Empty;
            passwordBox.Password = String.Empty;
        }

        void Logger_MessengerSent(object sender, RequestFinishedEventArgs e)
        {
            LoginJsonResponse x = e.Response as LoginJsonResponse;
            waitIndicator.Visibility = Visibility.Collapsed;
            if (!e.Response.HasError())
            {
                MessageBoxResult m = MessageBox.Show(x.error.ToString(), x.access_token.ToString(), MessageBoxButton.OK);
                if (m == MessageBoxResult.OK)
                {
                    NavigationService.Navigate(new Uri("/Pages/LoggedIn.xaml?", UriKind.Relative));
                }
            }
            else
            {
                MessageBoxResult m = MessageBox.Show(x.error.ToString(), x.error_description.ToString(), MessageBoxButton.OK);
                if (m == MessageBoxResult.OK)
                {
                    NavigationService.Navigate(new Uri("/Pages/MainPage.xaml?", UriKind.Relative));
                }
            }
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            waitIndicator.Visibility = Visibility.Visible;
            RegisterCommand Registers = new RegisterCommand();
            Registers.RequestFinished += new RequestFinishedEventHandler(Registers_MessengerSent);
            Registers.Register(new UserCredentials(userNameRegister.Text, passwordRegister1.Password));
            userNameRegister.Text = String.Empty;
            passwordRegister1.Password = String.Empty;
            passwordRegister2.Password = String.Empty;
        }

        private void Registers_MessengerSent(object sender, RequestFinishedEventArgs e)
        {
            ServerJsonResponse x = e.Response as ServerJsonResponse;
            waitIndicator.Visibility = Visibility.Collapsed;
            if (!e.Response.HasError())
            {
                MessageBoxResult m = MessageBox.Show(x.error.ToString(), x.message.ToString(), MessageBoxButton.OK);
                if (m == MessageBoxResult.OK)
                {
                    pano.DefaultItem = pano.Items[0];
                }
            }
            else
            {
                MessageBoxResult m = MessageBox.Show(x.error.ToString(), x.error_description.ToString(), MessageBoxButton.OK);
            }
        }
    }
}