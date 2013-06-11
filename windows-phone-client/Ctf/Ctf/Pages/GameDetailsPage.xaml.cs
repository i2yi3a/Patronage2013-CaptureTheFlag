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
using RestSharp;
using Ctf.ApplicationTools;

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

        //why no Task to return?
        private async void GetGameInfo(string gid)
        {
            System.Diagnostics.Debug.WriteLine("Detials of game id: " + gid);
            GameInfoCommand GameInfo = new GameInfoCommand(gid);
            GameInfo.RequestFinished += new RequestFinishedEventHandler(Page_RequestFinished);
            GameInfo.RequestFinished += new RequestFinishedEventHandler(NetworkService.RequestFinished_Event);
            RestRequestAsyncHandle handle = GameInfo.GetGameInfo();
            await NetworkService.AbortIfNoNetworkAsync(handle);
        }

        private void EditGame_Button_Click(object sender, EventArgs e)
        {
            EditGameCommand EditGame = new EditGameCommand(gameDetails.Id);
            //EditGame.EditGame(new Game("zzzzz", "zzzzzz", "519a6d6de4b06da65947762a" , "01-06-2013 10:13:00", 5400000, 12, 10, new Localization("Warsaw, Polska", new LatLng(15, 15), 1500)));    
        }

        private async void DeleteGame_Button_Click(object sender, EventArgs e)
        {
            DeleteCommand DeleteGameO = new DeleteCommand(gameDetails.Id);
            DeleteGameO.RequestFinished += new RequestFinishedEventHandler(Sign_RequestFinished);
            DeleteGameO.RequestFinished += new RequestFinishedEventHandler(NetworkService.RequestFinished_Event);
            RestRequestAsyncHandle handle = DeleteGameO.DeleteGame();
            await NetworkService.AbortIfNoNetworkAsync(handle);
        }

        private async void GameSignIn_Button_Click(object sender, EventArgs e)
        {
            SignInCommand SignInGame = new SignInCommand(gameDetails.Id);
            SignInGame.RequestFinished += new RequestFinishedEventHandler(Sign_RequestFinished);
            SignInGame.RequestFinished += new RequestFinishedEventHandler(NetworkService.RequestFinished_Event);
            RestRequestAsyncHandle handle = SignInGame.SignIn();
            await NetworkService.AbortIfNoNetworkAsync(handle);
        }

        private async void GameSignOut_Button_Click(object sender, EventArgs e)
        {
            SignOutCommand SignOutGame = new SignOutCommand(gameDetails.Id);
            SignOutGame.RequestFinished += new RequestFinishedEventHandler(Sign_RequestFinished);
            SignOutGame.RequestFinished += new RequestFinishedEventHandler(NetworkService.RequestFinished_Event);
            RestRequestAsyncHandle handle = SignOutGame.SignOut();
            await NetworkService.AbortIfNoNetworkAsync(handle);
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

            var ApplicationBarMenuItemsJoin = new ApplicationBarMenuItem() { Text = AppResources.GameDetailsApplicationBarMenuItemJoin };
            ApplicationBarMenuItemsJoin.Click += GameSignIn_Button_Click;
            ApplicationBar.MenuItems.Add(ApplicationBarMenuItemsJoin);

            var ApplicationBarMenuItemsLeave = new ApplicationBarMenuItem() { Text = AppResources.GameDetailsApplicationBarMenuItemLeave };
            ApplicationBarMenuItemsLeave.Click += GameSignOut_Button_Click;
            ApplicationBar.MenuItems.Add(ApplicationBarMenuItemsLeave);

            var ApplicationBarMenuItemsMap = new ApplicationBarMenuItem() { Text = AppResources.GameDetailsApplicationBarMenuItemMap };
            ApplicationBar.MenuItems.Add(ApplicationBarMenuItemsMap);

            var ApplicationBarMenuItemsEdit = new ApplicationBarMenuItem() { Text = AppResources.GameDetailsApplicationBarMenuItemEdit };
            ApplicationBar.MenuItems.Add(ApplicationBarMenuItemsEdit);

            var ApplicationBarMenuItemsDelete = new ApplicationBarMenuItem() { Text = AppResources.GameDetailsApplicationBarMenuItemDelete };
            ApplicationBarMenuItemsDelete.Click += DeleteGame_Button_Click;
            ApplicationBar.MenuItems.Add(ApplicationBarMenuItemsDelete);
        }
    }
}