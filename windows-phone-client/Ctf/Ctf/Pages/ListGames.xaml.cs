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
using RestSharp;
using System.Threading.Tasks;
using System.Reflection;
using System.Text.RegularExpressions;
using PhoneApp3;

namespace Ctf.Pages
{
    public partial class ListGames : PhoneApplicationPage
    {
        //TODO: HasError doesn't work correctly in NearbyGames
        private ObservableCollection<GameHeader> MyGames;
        private ObservableCollection<GameHeader> NearestGames;
        private ObservableCollection<GameHeader> AllGames;
        private ObservableCollection<GameHeader> CompletedGames;
        private ObservableCollection<GameHeader> AllGamesBase;
        //private Task<RestRequestAsyncHandle> task;
        //private Task networkTask;
        //private Task otherTask;
        Filterer f;
        List<double> MyPosition;
        Geo g;
        double range;
        Dictionary<int, string> pivotsText;

        public ListGames()
        {
            InitializeComponent();
            InitializeApplicationBar();
            this.MainPivot.SelectionChanged += ThisPivot_SelectionChanged;

            AllGamesBase = new ObservableCollection<GameHeader>();
            //AllGamesBase.CollectionChanged += AllGamesBase_CollectionChanged;


            g = new Geo();
            filter = new FilterNext();
            collection = new ObservableCollection<GameHeader>();
            collection = AllGamesBase;
            filteredCollection = new ObservableCollection<GameHeader>();
            MyGames = new ObservableCollection<GameHeader>();
            NearestGames = new ObservableCollection<GameHeader>();
            AllGames = new ObservableCollection<GameHeader>();
            CompletedGames = new ObservableCollection<GameHeader>();
            pivotsText = new Dictionary<int, string>();
            //how tosee ppivot item count ? replace constant
            for (int i = 0; i < 4; i++)
            {
                pivotsText.Add(i, String.Empty);
            }

            f = new Filterer();
            //NetworkService.DoInBackground();
            //networkTask = NetworkService.WorkAsync();
            //Debug.WriteLine("Before calling OtherAsync()");
            //otherTask = NetworkService.OtherAsync();
            //Debug.WriteLine("Exited to UI after calling OtherAsync()");

            //List<double> LatLng1 = new List<double>() { 40.0, 50.0 };
            //List<double> LatLng2 = new List<double>() { 50.0, 60.0 };
            //Debug.WriteLine("Distance between: " + LatLng1[0] + ", " + LatLng1[1] + " and " +  LatLng2[0] + ", " + LatLng2[1] + " is " + Geo.Distance(LatLng1,LatLng2) + "km");

            MyPosition = new List<double>() { 53.0, 14.0 };
            range = 1000;

        }

        void AllGamesBase_CollectionChanged(object sender, System.Collections.Specialized.NotifyCollectionChangedEventArgs e)
        {
            //InitializeCollectionsAndBind();
        }

        private void InitializeCollectionsAndBind()
        {
            //TODO: do in background thread
            InitializeCollection(AllGamesBase, MyGames);
            InitializeCollection(AllGamesBase, NearestGames);
            InitializeCollection(AllGamesBase, AllGames);
            InitializeCollection(AllGamesBase, CompletedGames);

            //TODO: do on UI thread
            MyGamesListControl.ItemsSource = MyGames;
            NearestGamesListControl.ItemsSource = NearestGames;
            AllGamesListControl.ItemsSource = AllGames;
            CompletedGamesListControl.ItemsSource = CompletedGames;
        }

        private async Task InitializeCollectionsAndBindAsync()
        {
            //await Task.Delay(10000);
            //TODO: do in background thread
            await InitializeCollectionAsync(AllGamesBase, MyGames);
            MyGamesListControl.ItemsSource = MyGames;

            await InitializeCollectionAsync(AllGamesBase, NearestGames);
            NearestGamesListControl.ItemsSource = NearestGames;

            await InitializeCollectionAsync(AllGamesBase, AllGames);
            AllGamesListControl.ItemsSource = AllGames;

            await InitializeCollectionAsync(AllGamesBase, CompletedGames);
            CompletedGamesListControl.ItemsSource = CompletedGames;

            //TODO: do on UI thread
        }

        private async Task InitializeCollectionAsync(ObservableCollection<GameHeader> sourceList, ObservableCollection<GameHeader> destinationList)
        {
            ObservableCollection<GameHeader> filteredCollection = null;
            if (sourceList == null)
                throw new ArgumentNullException();
            if (destinationList == null)
                throw new ArgumentNullException();
            destinationList.Clear();

            if (destinationList == MyGames)
            {
                filteredCollection = await filter.ByOwnerAsync(sourceList, ApplicationSettings.Instance.LoggedUsername);
                Debug.WriteLine("MyGames elements:" + filteredCollection.Count);
            }
            else if (destinationList == NearestGames)
            {
                filteredCollection = await filter.ByDistanceAsync(sourceList, MyPosition, range);
                Debug.WriteLine("NearestGames elements:" + filteredCollection.Count);
            }
            else if (destinationList == CompletedGames)
            {
                filteredCollection = await filter.ByStatusAsync(sourceList, "COMPLETED");
                Debug.WriteLine("CompletedGames elements:" + filteredCollection.Count);
            }
            else
            {
                filteredCollection = sourceList;
                Debug.WriteLine("AllGames elements:" + filteredCollection.Count);
            }



            foreach (GameHeader item in filteredCollection)
            {
                destinationList.Add(item);
            }
        }

        private void InitializeCollection(ObservableCollection<GameHeader> sourceList, ObservableCollection<GameHeader> destinationList)
        {
            if (sourceList == null)
                throw new ArgumentNullException();
            if (destinationList == null)
                throw new ArgumentNullException();
            destinationList.Clear();
            foreach (GameHeader item in sourceList)
            {
                destinationList.Add(item);
            }
        }

        private void FetchAllGamesBase()
        {
            if (AllGamesBase == null)
                throw new ArgumentNullException();
            if (!AllGamesBase.Any())
                FetchGamesOnce_Action();
        }

        private async Task FetchAllGamesBaseAsync()
        {
            if (AllGamesBase == null)
                throw new ArgumentNullException();
            if (!AllGamesBase.Any())
                await FetchGamesOnce_ActionAsync();
        }

        //protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        //{
        //    base.OnNavigatedTo(e);
        //    if (ApplicationSettings.Instance.HasLoginInfo())
        //    {
        //        MainPivot.Title = String.Format(AppResources.ListGamesLoggedAs, ApplicationSettings.Instance.LoggedUsername);
        //    }
        //    FetchAllGamesBase();
        //}

        protected async override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            if (ApplicationSettings.Instance.HasLoginInfo())
            {
                MainPivot.Title = String.Format(AppResources.ListGamesLoggedAs, ApplicationSettings.Instance.LoggedUsername);
            }
            await FetchAllGamesBaseAsync();
        }

        private void FetchMyGames_Action()
        {
            System.Diagnostics.Debug.WriteLine("Fetch for pivot item: " + this.MainPivot.SelectedIndex);
            //TODO: Change argument to GameFilter with name, status, owner only when implemented on server
            FetchGamesCommand<GamesList> FetchMyGames = new FetchGamesCommand<GamesList>(new GameHeader("", "", ApplicationSettings.Instance.LoggedUsername));
            FetchMyGames.RequestFinished += new RequestFinishedEventHandler(FetchMyGames_Event);
            FetchMyGames.ExcecuteAsyncCommand();
            System.Diagnostics.Debug.WriteLine("Exited to => private void FetchMyGames_Action()");
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

        private void FetchGamesOnce_Action()
        {
            System.Diagnostics.Debug.WriteLine("Fetch for pivot item: " + this.MainPivot.SelectedIndex);
            FetchGamesCommand<GamesList> FetchAllGamesOnce = new FetchGamesCommand<GamesList>();
            FetchAllGamesOnce.RequestFinished += new RequestFinishedEventHandler(FetchAllGamesOnce_Event);
            RestRequestAsyncHandle handle = FetchAllGamesOnce.ExcecuteAsyncCommand();
            System.Diagnostics.Debug.WriteLine("Pass async");
            //handle.Abort();

        }

        private async Task FetchGamesOnce_ActionAsync()
        {
            System.Diagnostics.Debug.WriteLine("Fetch for pivot item: " + this.MainPivot.SelectedIndex);
            FetchGamesCommand<GamesList> FetchAllGamesOnce = new FetchGamesCommand<GamesList>();
            FetchAllGamesOnce.RequestFinished += new RequestFinishedEventHandler(FetchAllGamesOnce_Event);
            FetchAllGamesOnce.RequestFinished += new RequestFinishedEventHandler(NetworkService.RequestFinished_Event);
            RestRequestAsyncHandle handle = FetchAllGamesOnce.ExcecuteAsyncCommand();
            System.Diagnostics.Debug.WriteLine("Pass async");
            await NetworkService.AbortIfNoNetworkAsync(handle);
            
                    //handle.Abort();
            //Obejście warninga:
            //return Task.Run(() => { });
        }

        private async void FetchAllGamesOnce_Event(object sender, RequestFinishedEventArgs e)
        {
            if (!e.Response.HasError())
            {
                GamesList gs = e.Response as GamesList;
                AllGamesBase.Clear();
                foreach (GameHeader g in gs.games)
                {
                    Debug.WriteLine("id:" + g.Id + ", name:" + g.Name + ", owner:" + g.Owner + ", status:" + g.Status + ", time start:" + g.TimeStart + ", players count:" + g.PlayersCount + ", players max:" + g.PlayersMax);
                    AllGamesBase.Add(g);
                }
                await InitializeCollectionsAndBindAsync();
            }
            else
            {
                ServerResponse x = e.Response as ServerResponse;
                MessageBoxResult m = MessageBox.Show(x.error.ToString(), x.error_description.ToString(), MessageBoxButton.OK);
            }
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

        
        private async void ThisPivot_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            String textBlock = FilterTextBox.Text;
            int index = this.MainPivot.SelectedIndex;
            String text = String.Empty;
            System.Diagnostics.Debug.WriteLine("Displayed pivot item: " + index);
            //ChooseFetchGamesCommand(this.MainPivot.SelectedIndex);
            if (pivotsText.TryGetValue(index, out text) && !text.Equals(textBlock))
            {
                switch (index)
                {
                    case 0:
                        pivotsText[0] = textBlock;
                        break;
                    case 1:
                        pivotsText[1] = textBlock;
                        break;
                    case 2:
                        pivotsText[2] = textBlock;
                        break;
                    case 3:
                        pivotsText[3] = textBlock;
                        break;
                }
                System.Diagnostics.Debug.WriteLine("Filtering on pivot: " + index + ", with text: " + textBlock);
                await FilterCollections();
            }
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

        private async void Refresh_ApplicationBarIconButton_Click(object sender, EventArgs e)
        {
            //task.Result.Abort();
            //InitializeCollectionsAndBind();
            List<double> latLng = await Geo.GetPhoneLocationAsync();
            Debug.WriteLine("My position is : " + latLng[0] + ", " + latLng[1]);
        }

        private void Search_ApplicationBarIconButton_Click(object sender, EventArgs e)
        {
            Debug.WriteLine("Is internet on: " + NetworkService.IsNetworkEnabled().ToString());
        }

        private void CreateGame_ApplicationBarIconButton_Click(object sender, EventArgs e)
        {
            NavigationService.Navigate(new Uri(String.Format("/Pages/CreateGamePage.xaml"), UriKind.Relative));
        }



        private FilterNext filter;
        private ObservableCollection<GameHeader> collection;
        private ObservableCollection<GameHeader> filteredCollection;

        private async Task FilterCollections()
        {
            String text = FilterTextBox.Text;
            switch (this.MainPivot.SelectedIndex)
            {
                case 0:
                    filteredCollection = await filter.ByOwnerAsync(MyGames, ApplicationSettings.Instance.LoggedUsername);
                    filteredCollection = await filter.ByNameAsync(filteredCollection, text);
                    System.Diagnostics.Debug.WriteLine("Collection for " + text + ":");
                    System.Diagnostics.Debug.WriteLine(filteredCollection.Count);
                    MyGamesListControl.ItemsSource = filteredCollection;
                    break;
                case 1:
                    filteredCollection = await filter.ByDistanceAsync(NearestGames, MyPosition, range);
                    filteredCollection = await filter.ByNameAsync(filteredCollection, text);
                    System.Diagnostics.Debug.WriteLine("Collection for " + text + ":");
                    System.Diagnostics.Debug.WriteLine(filteredCollection.Count);
                    NearestGamesListControl.ItemsSource = filteredCollection;
                    break;
                case 2:
                    filteredCollection = await filter.ByNameAsync(AllGames, text);
                    System.Diagnostics.Debug.WriteLine("Collection for " + text + ":");
                    System.Diagnostics.Debug.WriteLine(filteredCollection.Count);
                    AllGamesListControl.ItemsSource = filteredCollection;
                    break;
                case 3:
                    filteredCollection = await filter.ByStatusAsync(CompletedGames, "COMPLETED");
                    filteredCollection = await filter.ByNameAsync(filteredCollection, text);
                    System.Diagnostics.Debug.WriteLine("Collection for " + text + ":");
                    System.Diagnostics.Debug.WriteLine(filteredCollection.Count);
                    CompletedGamesListControl.ItemsSource = filteredCollection;
                    break;
            }
        }


        // Same functionality as private void ThisPivot_SelectionChanged(object sender, SelectionChangedEventArgs e)
        private async void FilterBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            await FilterCollections();
            //String text = (sender as TextBox).Text;
            //switch (this.MainPivot.SelectedIndex)
            //{
            //    case 0:
            //        filteredCollection = await filter.ByOwnerAsync(MyGames, ApplicationSettings.Instance.LoggedUsername);
            //        filteredCollection = await filter.ByNameAsync(filteredCollection, text);
            //        System.Diagnostics.Debug.WriteLine("Collection for " + text + ":");
            //        System.Diagnostics.Debug.WriteLine(filteredCollection.Count);
            //        MyGamesListControl.ItemsSource = filteredCollection;
            //        break;
            //    case 1:
            //        filteredCollection = await filter.ByDistanceAsync(NearestGames, MyPosition, range);
            //        System.Diagnostics.Debug.WriteLine("Collection for " + text + ":");
            //        System.Diagnostics.Debug.WriteLine(filteredCollection.Count);
            //        NearestGamesListControl.ItemsSource = filteredCollection;
            //        break;
            //    case 2:
            //        filteredCollection = await filter.ByNameAsync(AllGames, text);
            //        System.Diagnostics.Debug.WriteLine("Collection for " + text + ":");
            //        System.Diagnostics.Debug.WriteLine(filteredCollection.Count);
            //        AllGamesListControl.ItemsSource = filteredCollection;
            //        break;
            //    case 3:
            //        filteredCollection = await filter.ByStatusAsync(CompletedGames, "COMPLETED");
            //        filteredCollection = await filter.ByNameAsync(filteredCollection, text);
            //        System.Diagnostics.Debug.WriteLine("Collection for " + text + ":");
            //        System.Diagnostics.Debug.WriteLine(filteredCollection.Count);
            //        CompletedGamesListControl.ItemsSource = filteredCollection;
            //        break;
            //}
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