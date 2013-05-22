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
using Ctf.Communication;
using System.Windows.Controls.Primitives;
using System.ComponentModel;
using Ctf.ApplicationTools;
using Ctf.Communication.DataObjects;
using Ctf.ApplicationTools.DataObjects;

namespace Ctf.Pages
{
    public partial class LoggedIn : PhoneApplicationPage
    {
        public LoggedIn()
        {
            InitializeComponent();
            //ApplicationSettings.Instance.UserChanged += UserHasChanged;
        }

        protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            if (ApplicationSettings.Instance.HasLoginInfo())
            {
                welcomeBlock.Text = ApplicationSettings.Instance.RetriveLoggedUser().username;
            }
        }

        private void Logout(object sender, RoutedEventArgs e)
        {
            LoginCommand Logger = new LoginCommand();
            if (Logger.LogOut())
                Debug.WriteLine("Logout SUCCESSFUL");
            else
                Debug.WriteLine("Logout FAILED");
            Debug.WriteLineIf(Logger.LoggedAs().username !=null, "Logged as:" + Logger.LoggedAs().username);

            if (NavigationService.CanGoBack)
            {
                NavigationService.GoBack();
            }
            else
            {
                NavigationService.Navigate(new Uri("/Pages/MainPage.xaml?", UriKind.Relative));
            }
        }

        //Check if it still works when deleted UserHasChanged
       /* public void UserHasChanged(object sender, EventArgs e)
        {
            if (ApplicationSettings.Instance.HasLoginInfo())
            {
                welcomeBlock.Text = ApplicationSettings.Instance.RetriveLoggedUser().username;
            }
            else
            {
                NavigationService.Navigate(new Uri("/Pages/MainPage.xaml?", UriKind.Relative));
            }
        }*/

        private void PhoneApplicaitonPage_BackKeyPress(object sender, System.ComponentModel.CancelEventArgs e)
        {
            e.Cancel = true;
        }

        private void GameInfo_Button_Click(object sender, RoutedEventArgs e)
        {
            GameInfoCommand GameInfo = new GameInfoCommand("519a6d6de4b06da65947762a");
            GameInfo.GetGameInfo();
        }
        
        private void EditGame_Button_Click(object sender, RoutedEventArgs e)
        {
            EditGameCommand EditGame = new EditGameCommand("519a6d6de4b06da65947762a");
            EditGame.EditGame(new Game("zzzzz", "zzzzzz", "519a6d6de4b06da65947762a" , "01-06-2013 10:13:00", 5400000, 12, 10, new Localization("Warsaw, Polska", new LatLng(15, 15), 1500)));
                  
        }

        private void GameSignIn_Button_Click(object sender, RoutedEventArgs e)
        {
            SignInCommand SignInGame = new SignInCommand("519a6d6de4b06da65947762a");
            SignInGame.SignIn();

        }

        private void GameSignOut_Button_Click(object sender, RoutedEventArgs e)
        {
            SignOutCommand SignOutGame = new SignOutCommand("519a6d6de4b06da65947762a");
            SignOutGame.SignOut();

        }

        private void creategamebox_Click(object sender, RoutedEventArgs e)
        {
            CreateCommand gaame = new CreateCommand();
            gaame.RequestFinished += new RequestFinishedEventHandler(gaame_RequestFinished);
            gaame.CreateGame(new Game("sproba", "jasna", "10-06-2013 10:13:00", 5400000, 12, 10, new Localization("Jasne błonia, Szczecin, Polska", new LatLng(15, 15), 1500)));
        }

        void gaame_RequestFinished(object sender, RequestFinishedEventArgs e)
        {
            
            
            if (!e.Response.HasError())
            {
               Game x = e.Response as Game; 
               MessageBoxResult m = MessageBox.Show(x.message.ToString(), x.id.ToString(), MessageBoxButton.OK);
            }
            else
            {
                MessageBoxResult m = MessageBox.Show(e.Response.error_description.ToString(), e.Response.error.ToString(), MessageBoxButton.OK);
            }


        }

        private void ListGames_Button_Click(object sender, RoutedEventArgs e)
        {
            NavigationService.Navigate(new Uri("/Views/ListGames.xaml?", UriKind.Relative));
        }
    }
}