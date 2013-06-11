using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using Ctf.ViewModels;
using Ctf.Communication;
using Ctf.Communication.DataObjects;
using Ctf.Models.DataObjects;
using Ctf.Resources;

namespace Ctf.Views
{
    public partial class GameDetailsPage : PhoneApplicationPage
    {
        private GameDetails gameDetails;

        public GameDetailsPage()
        {
            InitializeComponent();
            gameDetails = new GameDetails();
            //this.DataContext = gameDetails;
            InitializeApplicationBar();
            
        }
        
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            string gid = String.Empty;
            if (NavigationContext.QueryString.TryGetValue("gid", out gid))
            {
                GetGameInfo(gid);
            }
        }

        private void GetGameInfo(string gid)
        {
            System.Diagnostics.Debug.WriteLine("Detials of game id: " + gid);
            GameInfoCommand GameInfo = new GameInfoCommand(gid);
            GameInfo.RequestFinished += new RequestFinishedEventHandler(Page_RequestFinished);
            GameInfo.GetGameInfo();
        }

        private void EditGame_Button_Click(object sender, RoutedEventArgs e)
        {
            EditGameCommand EditGame = new EditGameCommand(gameDetails.Id);
            //EditGame.EditGame(new Game("zzzzz", "zzzzzz", "519a6d6de4b06da65947762a" , "01-06-2013 10:13:00", 5400000, 12, 10, new Localization("Warsaw, Polska", new LatLng(15, 15), 1500)));    
        }

        private void DeleteGame_Button_Click(object sender, RoutedEventArgs e)
        {
            DeleteCommand DeleteGameO = new DeleteCommand(gameDetails.Id);
            DeleteGameO.RequestFinished += new RequestFinishedEventHandler(Sign_RequestFinished);
            DeleteGameO.DeleteGame();
        }

        private void GameSignIn_Button_Click(object sender, RoutedEventArgs e)
        {
            SignInCommand SignInGame = new SignInCommand(gameDetails.Id);
            SignInGame.RequestFinished += new RequestFinishedEventHandler(Sign_RequestFinished);
            SignInGame.SignIn();
        }

        private void GameSignOut_Button_Click(object sender, RoutedEventArgs e)
        {
            SignOutCommand SignOutGame = new SignOutCommand(gameDetails.Id);
            SignOutGame.RequestFinished += new RequestFinishedEventHandler(Sign_RequestFinished);
            SignOutGame.SignOut();
        }

        void Sign_RequestFinished(object sender, RequestFinishedEventArgs e)
        {
            if (!e.Response.HasError())
            {
                ServerResponse x = e.Response as ServerResponse;
                MessageBoxResult m = MessageBox.Show("success", x.error_code.ToString(), MessageBoxButton.OK);
            }
            else
            {
                MessageBoxResult m = MessageBox.Show(e.Response.error_description.ToString(), e.Response.error.ToString(), MessageBoxButton.OK);
            }
        }

        void Page_RequestFinished(object sender, RequestFinishedEventArgs e)
        {
            if (!e.Response.HasError())
            {
                GameDetails x = e.Response as GameDetails;
                //MessageBoxResult m = MessageBox.Show(x.Name.ToString(), x.Id.ToString(), MessageBoxButton.OK);
                //gameDetails.Name = x.Name;
                gameDetails = x;
                this.DataContext = gameDetails;
                //ContentPanel.DataContext = x;
                //gameDetails = x;
                //PlayersList.DataContext = x.players;
            }
            else
            {
                MessageBoxResult m = MessageBox.Show(e.Response.error_description.ToString(), e.Response.error.ToString(), MessageBoxButton.OK);
            }
        }

        protected void InitializeApplicationBar()
        {
            ApplicationBar = new ApplicationBar { IsVisible = true, IsMenuEnabled = true };

            var ApplicationBarButtonCreate = new ApplicationBarIconButton(new Uri("/Images/new.png", UriKind.Relative)) { Text = AppResources.ListGamesApplicationBarButtonCreate };
            //ApplicationBarButtonCreate.Click += CreateGame_ApplicationBarIconButton_Click;
            ApplicationBar.Buttons.Add(ApplicationBarButtonCreate);

            var ApplicationBarButtonSearch = new ApplicationBarIconButton(new Uri("/Images/feature.search.png", UriKind.Relative)) { Text = AppResources.ListGamesApplicationBarButtonSearch };
            //ApplicationBarButtonSearch.Click += Search_ApplicationBarIconButton_Click;
            ApplicationBar.Buttons.Add(ApplicationBarButtonSearch);

            var ApplicationBarButtonRefresh = new ApplicationBarIconButton(new Uri("/Images/refresh.png", UriKind.Relative)) { Text = AppResources.ListGamesApplicationBarButtonRefresh };
            //ApplicationBarButtonRefresh.Click += Refresh_ApplicationBarIconButton_Click;
            ApplicationBar.Buttons.Add(ApplicationBarButtonRefresh);

            var ApplicationBarMenuItemsProfile = new ApplicationBarMenuItem() { Text = AppResources.ListGamesApplicationBarMenuItemProfile };
            ApplicationBar.MenuItems.Add(ApplicationBarMenuItemsProfile);

            var ApplicationBarMenuItemsSettings = new ApplicationBarMenuItem() { Text = AppResources.ListGamesApplicationBarMenuItemSettings };
            ApplicationBar.MenuItems.Add(ApplicationBarMenuItemsSettings);

            var ApplicationBarMenuItemsLogout = new ApplicationBarMenuItem() { Text = AppResources.ListGamesApplicationBarMenuItemLogout };
            //ApplicationBarMenuItemsLogout.Click += Logout_ApplicationBarMenuItem_Click;
            ApplicationBar.MenuItems.Add(ApplicationBarMenuItemsLogout);
        }
    }
}