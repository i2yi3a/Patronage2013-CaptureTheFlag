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
using Ctf.Communication.DataObjects;
using Ctf.Communication;
using Ctf.Models;
using System.Diagnostics;
using Ctf.ApplicationTools;
using Ctf.ApplicationTools.DataObjects;
using Ctf.Models.DataObjects;
using System.Threading;
using Ctf.Resources;
using System.Collections.ObjectModel;

namespace Ctf.Pages
{
    public partial class ListGames : PhoneApplicationPage
    {
        //TODO: HasError doesn't work correctly in NearbyGames
        private ObservableCollection<GameHeader> MyGames;
        private ObservableCollection<GameHeader> NearestGames;
        private ObservableCollection<GameHeader> AllGames;
        private ObservableCollection<GameHeader> CompletedGames;

        public ListGames()
        {
            InitializeComponent();
            InitializeApplicationBar();
            this.MainPivot.SelectionChanged += ThisPivot_SelectionChanged;

            MyGames = new ObservableCollection<GameHeader>();
            NearestGames = new ObservableCollection<GameHeader>();
            AllGames = new ObservableCollection<GameHeader>();
            CompletedGames = new ObservableCollection<GameHeader>();

            MyGamesListControl.ItemsSource = MyGames;
            NearestGamesListControl.ItemsSource = NearestGames;
            AllGamesListControl.ItemsSource = AllGames;
            CompletedGamesListControl.ItemsSource = CompletedGames;
        }

        protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            if (ApplicationSettings.Instance.HasLoginInfo())
            {
                MainPivot.Title = String.Format(AppResources.ListGamesLoggedAs, ApplicationSettings.Instance.LoggedUsername);
            }
        }

        private void FetchMyGames_Action()
        {
            System.Diagnostics.Debug.WriteLine("Fetch for pivot item: " + this.MainPivot.SelectedIndex);
            //TODO: Change argument to GameFilter with name, status, owner only when implemented on server
            FetchGamesCommand<GamesList> FetchMyGames = new FetchGamesCommand<GamesList>(new GameHeader("", "", ApplicationSettings.Instance.LoggedUsername));
            FetchMyGames.RequestFinished += new RequestFinishedEventHandler(FetchMyGames_Event);
            FetchMyGames.ExcecuteAsyncCommand();
        }

        private void FetchNearestGames_Action()
        {
            System.Diagnostics.Debug.WriteLine("Fetch for pivot item: " + this.MainPivot.SelectedIndex);
            //TODO: Change argument to LocationFilter with LatLng from gps, range, status only when implemented on server
            FetchGamesCommand<GamesList> FetchNearestGames = new FetchGamesCommand<GamesList>(new LocationFilter(50.0, 50.0, 1000000.0, GameStatus.NEW));
            FetchNearestGames.RequestFinished += new RequestFinishedEventHandler(FetchNearestGames_Event);
            FetchNearestGames.ExcecuteAsyncCommand();
        }

        private void FetchAllGames_Action()
        {
            System.Diagnostics.Debug.WriteLine("Fetch for pivot item: " + this.MainPivot.SelectedIndex);
            FetchGamesCommand<GamesList> FetchAllGames = new FetchGamesCommand<GamesList>();
            FetchAllGames.RequestFinished += new RequestFinishedEventHandler(FetchAllGames_Event);
            FetchAllGames.ExcecuteAsyncCommand();
        }

        private void FetchCompletedGames_Action()
        {
            System.Diagnostics.Debug.WriteLine("Fetch for pivot item: " + this.MainPivot.SelectedIndex);
            //TODO: Change argument to GameFilter with name, status, owner only when implemented on server
            FetchGamesCommand<GamesList> FetchCompletedGames = new FetchGamesCommand<GamesList>(new GameHeader("", GameStatus.COMPLETED.ToString(), ApplicationSettings.Instance.LoggedUsername));
            FetchCompletedGames.RequestFinished += new RequestFinishedEventHandler(FetchCompletedGames_Event);
            FetchCompletedGames.ExcecuteAsyncCommand();
        }

        private void FetchMyGames_Event(object sender, RequestFinishedEventArgs e)
        {
            if (!e.Response.HasError())
            {
                GamesList gs = e.Response as GamesList;
                MyGames.Clear();
                foreach (GameHeader g in gs.games)
                {
                    Debug.WriteLine("id:" + g.Id + ", name:" + g.Name + ", owner:" + g.Owner + ", status:" + g.Status);
                    MyGames.Add(g);
                }
            }
            else
            {
                ServerResponse x = e.Response as ServerResponse;
                MessageBoxResult m = MessageBox.Show(x.error.ToString(), x.error_description.ToString(), MessageBoxButton.OK);
            }
        }

        private void FetchNearestGames_Event(object sender, RequestFinishedEventArgs e)
        {
            if (!e.Response.HasError())
            {
                System.Diagnostics.Debug.WriteLine("e.Response.error: " + e.Response.error);
                System.Diagnostics.Debug.WriteLine("e.Response.error_description: " + e.Response.error_description);
                ServerResponse s = e.Response as ServerResponse;
                System.Diagnostics.Debug.WriteLine("s.error: " + s.error);
                System.Diagnostics.Debug.WriteLine("s.error_description: " + s.error_description);
                GamesList gs = e.Response as GamesList;
                NearestGames.Clear();
                foreach (GameHeader g in gs.games)
                {
                    Debug.WriteLine("id:" + g.Id + ", name:" + g.Name + ", owner:" + g.Owner + ", status:" + g.Status);
                    NearestGames.Add(g);
                }
            }
            else
            {
                ServerResponse x = e.Response as ServerResponse;
                MessageBoxResult m = MessageBox.Show(x.error.ToString(), x.error_description.ToString(), MessageBoxButton.OK);
            }
        }

        private void FetchAllGames_Event(object sender, RequestFinishedEventArgs e)
        {
            if (!e.Response.HasError())
            {
                GamesList gs = e.Response as GamesList;
                AllGames.Clear();
                foreach (GameHeader g in gs.games)
                {
                    Debug.WriteLine("id:" + g.Id + ", name:" + g.Name + ", owner:" + g.Owner + ", status:" + g.Status);
                    AllGames.Add(g);
                }
            }
            else
            {
                ServerResponse x = e.Response as ServerResponse;
                MessageBoxResult m = MessageBox.Show(x.error.ToString(), x.error_description.ToString(), MessageBoxButton.OK);
            }
        }

        private void FetchCompletedGames_Event(object sender, RequestFinishedEventArgs e)
        {
            if (!e.Response.HasError())
            {
                GamesList gs = e.Response as GamesList;
                CompletedGames.Clear();
                foreach (GameHeader g in gs.games)
                {
                    Debug.WriteLine("id:" + g.Id + ", name:" + g.Name + ", owner:" + g.Owner + ", status:" + g.Status);
                    CompletedGames.Add(g);
                }
            }
            else
            {
                ServerResponse x = e.Response as ServerResponse;
                MessageBoxResult m = MessageBox.Show(x.error.ToString(), x.error_description.ToString(), MessageBoxButton.OK);
            }
        }

        private void ChooseFetchGamesCommand(int selection)
        {
            switch (selection)
            {
                case 0:
                    if (!MyGames.Any())
                    {
                        FetchMyGames_Action();
                    }
                    break;
                case 1:
                    if (!NearestGames.Any())
                    {
                        FetchNearestGames_Action();
                    }
                    break;
                case 2:
                    if (!AllGames.Any())
                    {
                        FetchAllGames_Action();
                    }
                    break;
                case 3:
                    if (!CompletedGames.Any())
                    {
                        FetchCompletedGames_Action();
                    }
                    break;
            }
        }

        private void GamesList_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            // If selected index is -1 (no selection) do nothing
            //if (GamesList.SelectedIndex == -1)
            //    return;
            ListBox lb = sender as ListBox;
            System.Diagnostics.Debug.WriteLine("Selected index: " + lb.SelectedIndex);
            if (lb.SelectedIndex == -1)
                return;

            GameIdentificator gid = lb.Items.ElementAt(lb.SelectedIndex) as GameIdentificator;
            System.Diagnostics.Debug.WriteLine("Selected game id: " + gid.id);

            // Navigate to the new page
            NavigationService.Navigate(new Uri(String.Format("/Pages/GameDetailsPage.xaml?gid={0}", gid.id), UriKind.Relative));

            // Reset selected index to -1 (no selection)
            lb.SelectedIndex = -1;
        }

        private void PhoneApplicaitonPage_BackKeyPress(object sender, System.ComponentModel.CancelEventArgs e)
        {
            base.OnBackKeyPress(e);
            if (NavigationService.CanGoBack)
            {
                while (NavigationService.RemoveBackEntry() != null)
                {
                    NavigationService.RemoveBackEntry();
                }
            }
        }

        private void ThisPivot_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("Displayed pivot item: " + this.MainPivot.SelectedIndex);
            ChooseFetchGamesCommand(this.MainPivot.SelectedIndex);
        }

        private void Logout_ApplicationBarMenuItem_Click(object sender, EventArgs e)
        {
            LoginCommand Logger = new LoginCommand();
            if (Logger.LogOut())
                Debug.WriteLine("Logout SUCCESSFUL");
            else
                Debug.WriteLine("Logout FAILED");
            Debug.WriteLineIf(Logger.LoggedAs().username != null, "Logged as:" + Logger.LoggedAs().username);

            if (NavigationService.CanGoBack)
            {
                NavigationService.GoBack();
            }
            else
            {
                NavigationService.Navigate(new Uri("/Pages/MainPage.xaml", UriKind.Relative));
            }
        }

        private void Refresh_ApplicationBarIconButton_Click(object sender, EventArgs e)
        {

        }

        private void Search_ApplicationBarIconButton_Click(object sender, EventArgs e)
        {

        }

        private void CreateGame_ApplicationBarIconButton_Click(object sender, EventArgs e)
        {
            NavigationService.Navigate(new Uri(String.Format("/Pages/CreateGamePage.xaml"), UriKind.Relative));
        }

        protected void InitializeApplicationBar()
        {
            ApplicationBar = new ApplicationBar { IsVisible = true, IsMenuEnabled = true };

            var ApplicationBarButtonCreate = new ApplicationBarIconButton(new Uri("/Images/new.png", UriKind.Relative)) { Text = AppResources.ListGamesApplicationBarButtonCreate };
            ApplicationBarButtonCreate.Click += CreateGame_ApplicationBarIconButton_Click;
            ApplicationBar.Buttons.Add(ApplicationBarButtonCreate);

            var ApplicationBarButtonSearch = new ApplicationBarIconButton(new Uri("/Images/feature.search.png", UriKind.Relative)) { Text = AppResources.ListGamesApplicationBarButtonSearch };
            ApplicationBarButtonSearch.Click += Search_ApplicationBarIconButton_Click;
            ApplicationBar.Buttons.Add(ApplicationBarButtonSearch);

            var ApplicationBarButtonRefresh = new ApplicationBarIconButton(new Uri("/Images/refresh.png", UriKind.Relative)) { Text = AppResources.ListGamesApplicationBarButtonRefresh };
            ApplicationBarButtonRefresh.Click += Refresh_ApplicationBarIconButton_Click;
            ApplicationBar.Buttons.Add(ApplicationBarButtonRefresh);

            var ApplicationBarMenuItemsProfile = new ApplicationBarMenuItem() { Text = AppResources.ListGamesApplicationBarMenuItemProfile };
            ApplicationBar.MenuItems.Add(ApplicationBarMenuItemsProfile);

            var ApplicationBarMenuItemsSettings = new ApplicationBarMenuItem() { Text = AppResources.ListGamesApplicationBarMenuItemSettings };
            ApplicationBar.MenuItems.Add(ApplicationBarMenuItemsSettings);

            var ApplicationBarMenuItemsLogout = new ApplicationBarMenuItem() { Text = AppResources.ListGamesApplicationBarMenuItemLogout };
            ApplicationBarMenuItemsLogout.Click += Logout_ApplicationBarMenuItem_Click;
            ApplicationBar.MenuItems.Add(ApplicationBarMenuItemsLogout);
        }
    }
}