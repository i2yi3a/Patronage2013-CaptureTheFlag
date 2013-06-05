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
        }
        
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            string gid = "";
            if (NavigationContext.QueryString.TryGetValue("gid", out gid))
            {
                System.Diagnostics.Debug.WriteLine("Detials of game id: " + gid);
                System.Diagnostics.Debug.WriteLine(AppResources.TestDebug);
                GameInfoCommand GameInfo = new GameInfoCommand(gid);
                GameInfo.RequestFinished += new RequestFinishedEventHandler(Page_RequestFinished);
                GameInfo.GetGameInfo();
            }
        }

        private void GameInfo_Button_Click(object sender, RoutedEventArgs e)
        {
            GameInfoCommand GameInfo = new GameInfoCommand("519a6d6de4b06da65947762a");
            GameInfo.GetGameInfo();
        }

        private void EditGame_Button_Click(object sender, RoutedEventArgs e)
        {
            EditGameCommand EditGame = new EditGameCommand("519a6d6de4b06da65947762a");
            //EditGame.EditGame(new Game("zzzzz", "zzzzzz", "519a6d6de4b06da65947762a" , "01-06-2013 10:13:00", 5400000, 12, 10, new Localization("Warsaw, Polska", new LatLng(15, 15), 1500)));    
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

        void Page_RequestFinished(object sender, RequestFinishedEventArgs e)
        {
            if (!e.Response.HasError())
            {
                GameDetails x = e.Response as GameDetails;
                MessageBoxResult m = MessageBox.Show(x.Name.ToString(), x.Id.ToString(), MessageBoxButton.OK);
                //gameDetails.Name = x.Name;
                this.DataContext = x;
                ContentPanel.DataContext = x;
                //gameDetails = x;
                PlayersList.DataContext = x.players;
            }
            else
            {
                MessageBoxResult m = MessageBox.Show(e.Response.error_description.ToString(), e.Response.error.ToString(), MessageBoxButton.OK);
            }
        }
    }
}