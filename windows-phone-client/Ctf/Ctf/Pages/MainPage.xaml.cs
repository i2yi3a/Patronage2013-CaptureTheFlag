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
        //TODO: Map server error/success response to localized strings
        //TODO XAML: Text in LoginErrorBlock does not fit, is there an auto scroll?
        private const string TEMPORARY_SECRET = "secret";

        public MainPage()
        {
            InitializeComponent();
            LoginButton.IsEnabled = false;
            RegisterButton.IsEnabled = false;
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

        private void LoginTextBox_Changed(object sender, RoutedEventArgs e)
        {
            String errorInfo = String.Empty;
            LoginButton.IsEnabled = false;
            if (!String.IsNullOrWhiteSpace(LoginUsernameBox.Text) &&
                LoginUsernameBox.Text.Length >= UserCredentials.MINIMAL_USERNAME_LENGTH &&
                LoginPasswordBox.Password.Length >= UserCredentials.MINIMAL_PASSWORD_LENGTH)
            {
                LoginButton.IsEnabled = true;
            }
            else
            {
                if (!String.IsNullOrWhiteSpace(LoginUsernameBox.Text) &&
                    LoginUsernameBox.Text.Length < UserCredentials.MINIMAL_USERNAME_LENGTH)
                {
                    errorInfo += String.Format(AppResources.MainPageMinimalUsernameLength, UserCredentials.MINIMAL_USERNAME_LENGTH);
                    errorInfo += Environment.NewLine;
                }
                if (!String.IsNullOrEmpty(LoginPasswordBox.Password) &&
                    LoginPasswordBox.Password.Length < UserCredentials.MINIMAL_PASSWORD_LENGTH)
                {
                    errorInfo += String.Format(AppResources.MainPageMinimalPasswordLength, UserCredentials.MINIMAL_PASSWORD_LENGTH);
                }
            }
            LoginErrorBlock.Text = errorInfo;
        }

        private void RegisterTextBox_Changed(object sender, RoutedEventArgs e)
        {
            String errorInfo = String.Empty;
            RegisterButton.IsEnabled = false;
            if (!String.IsNullOrWhiteSpace(RegisterUsernameBox.Text) &&
                RegisterUsernameBox.Text.Length >= UserCredentials.MINIMAL_USERNAME_LENGTH &&
                RegisterFirstPasswordBox.Password.Length >= UserCredentials.MINIMAL_PASSWORD_LENGTH &&
                RegisterSecondPasswordBox.Password.Length >= UserCredentials.MINIMAL_PASSWORD_LENGTH &&
                RegisterFirstPasswordBox.Password.Equals(RegisterSecondPasswordBox.Password))
            {
                RegisterButton.IsEnabled = true;
            }
            else
            {
                if (!String.IsNullOrWhiteSpace(RegisterUsernameBox.Text) &&
                    RegisterUsernameBox.Text.Length < UserCredentials.MINIMAL_USERNAME_LENGTH)
                {
                    errorInfo += String.Format(AppResources.MainPageMinimalUsernameLength, UserCredentials.MINIMAL_USERNAME_LENGTH);
                    errorInfo += Environment.NewLine;
                }
                if (!String.IsNullOrEmpty(RegisterFirstPasswordBox.Password) &&
                    RegisterFirstPasswordBox.Password.Length < UserCredentials.MINIMAL_PASSWORD_LENGTH)
                {
                    errorInfo += String.Format(AppResources.MainPageMinimalPasswordLength, UserCredentials.MINIMAL_PASSWORD_LENGTH);
                    errorInfo += Environment.NewLine;
                }
                if (!String.IsNullOrEmpty(RegisterSecondPasswordBox.Password) &&
                    !RegisterFirstPasswordBox.Password.Equals(RegisterSecondPasswordBox.Password))
                {
                    errorInfo += AppResources.MainPageMatchingPasswords;
                }
            }
            RegisterErrorBlock.Text = errorInfo;
        }

        private void Login_Action(object sender, RoutedEventArgs e)
        {
            WaitIndicator.Visibility = Visibility.Visible;
            LoginCommand Logger = new LoginCommand();
            Logger.RequestFinished += new RequestFinishedEventHandler(Login_Event);
            Logger.LoginAs(new UserCredentials(LoginUsernameBox.Text, LoginPasswordBox.Password), TEMPORARY_SECRET);
        }

        private void Register_Action(object sender, RoutedEventArgs e)
        {
            WaitIndicator.Visibility = Visibility.Visible;
            RegisterCommand Register = new RegisterCommand();
            Register.RequestFinished += new RequestFinishedEventHandler(Register_Event);
            Register.RegisterAs(new UserCredentials(RegisterUsernameBox.Text, RegisterFirstPasswordBox.Password));
        }

        void Login_Event(object sender, RequestFinishedEventArgs e)
        {
            AuthorizationToken x = e.Response as AuthorizationToken;
            WaitIndicator.Visibility = Visibility.Collapsed;
            if (!x.HasError())
            {
                LoginUsernameBox.Text = String.Empty;
                LoginPasswordBox.Password = String.Empty;
                NavigationService.Navigate(new Uri("/Pages/ListGames.xaml?", UriKind.Relative));
            }
            else
            {
                MessageBoxResult m = MessageBox.Show(x.error_description.ToString(), x.error.ToString(), MessageBoxButton.OK);
            }
        }

        private void Register_Event(object sender, RequestFinishedEventArgs e)
        {
            ServerResponse x = e.Response as ServerResponse;
            WaitIndicator.Visibility = Visibility.Collapsed;
            if (!x.HasError())
            {
                MessageBoxResult m = MessageBox.Show(x.message.ToString(), x.error_code.ToString(), MessageBoxButton.OK);
                LoginUsernameBox.Text = RegisterUsernameBox.Text;
                LoginPasswordBox.Password = RegisterFirstPasswordBox.Password;
                RegisterUsernameBox.Text = String.Empty;
                RegisterFirstPasswordBox.Password = String.Empty;
                RegisterSecondPasswordBox.Password = String.Empty;
                MainPanorama.DefaultItem = MainPanorama.Items.First();
            }
            else
            {
                MessageBoxResult m = MessageBox.Show(x.error.ToString(), x.error_description.ToString(), MessageBoxButton.OK);
            }
        }
    }
}