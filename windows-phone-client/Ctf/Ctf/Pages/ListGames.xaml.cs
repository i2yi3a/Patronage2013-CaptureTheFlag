using Ctf.ApplicationTools;
using Ctf.Communication;
using Ctf.Communication.DataObjects;
using Ctf.Models;
using Ctf.Models.DataObjects;
using Ctf.Resources;
using Ctf.ViewModels;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;

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

        private CollectionFilter filter;
        private ObservableCollection<GameHeader> filteredCollection;

        private const double LOCATION_PROXIMITY = 1000.0;
        Dictionary<int, string> pivotsText;

        public ListGames()
        {
            InitializeComponent();
            InitializeApplicationBar();

            AllGamesBase = new ObservableCollection<GameHeader>();
            MyGames = new ObservableCollection<GameHeader>();
            NearestGames = new ObservableCollection<GameHeader>();
            AllGames = new ObservableCollection<GameHeader>();
            CompletedGames = new ObservableCollection<GameHeader>();

            filter = new CollectionFilter();
            filteredCollection = new ObservableCollection<GameHeader>();

            pivotsText = new Dictionary<int, string>();
            //how to see pivot item count ? replace constant
            for (int i = 0; i < 4; i++)
            {
                pivotsText.Add(i, String.Empty);
            }
        }

        private async Task InitializeCollectionsAndBindAsync()
        {
            Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task InitializeCollectionsAndBindAsync()", "Begin"));
            await InitializeCollectionAsync(AllGamesBase, MyGames);
            MyGamesListControl.ItemsSource = MyGames;

            await InitializeCollectionAsync(AllGamesBase, NearestGames);
            NearestGamesListControl.ItemsSource = NearestGames;

            await InitializeCollectionAsync(AllGamesBase, AllGames);
            AllGamesListControl.ItemsSource = AllGames;

            await InitializeCollectionAsync(AllGamesBase, CompletedGames);
            CompletedGamesListControl.ItemsSource = CompletedGames;
            Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task InitializeCollectionsAndBindAsync()", "End"));
        }

        private async Task InitializeCollectionAsync(ObservableCollection<GameHeader> sourceList, ObservableCollection<GameHeader> destinationList)
        {
            Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task InitializeCollectionAsync()", "Begin"));
            ObservableCollection<GameHeader> filteredCollection = null;
            if (sourceList == null)
                throw new ArgumentNullException();
            if (destinationList == null)
                throw new ArgumentNullException();
            destinationList.Clear();

            if (destinationList == MyGames)
            {
                filteredCollection = await filter.ByOwnerAsync(sourceList, ApplicationSettings.Instance.LoggedUsername);
                Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task InitializeCollectionAsync()", "MyGames elements:" + filteredCollection.Count));
            }
            else if (destinationList == NearestGames)
            {
                List<double> myPosition = await Geo.GetPhoneLocationListAsync();
                filteredCollection = await filter.ByDistanceAsync(sourceList, myPosition, LOCATION_PROXIMITY);
                Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task InitializeCollectionAsync()", "NearestGames elements:" + filteredCollection.Count));
            }
            else if (destinationList == CompletedGames)
            {
                filteredCollection = await filter.ByStatusAsync(sourceList, "COMPLETED");
                Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task InitializeCollectionAsync()", "CompletedGames elements:" + filteredCollection.Count));
            }
            else
            {
                filteredCollection = sourceList;
                Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task InitializeCollectionAsync()", "AllGames elements:" + filteredCollection.Count));
            }

            foreach (GameHeader item in filteredCollection)
            {
                destinationList.Add(item);
            }
            Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task InitializeCollectionAsync()", "End"));
        }

        private async Task FetchAllGamesBaseAsync()
        {
            Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task FetchAllGamesBaseAsync()", "Begin"));
            if (AllGamesBase == null)
                throw new ArgumentNullException();
            if (!AllGamesBase.Any())
                await FetchGames_ActionAsync();
            Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task FetchAllGamesBaseAsync()", "End"));
        }

        private Task FetchGames_ActionAsync()
        {
            Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Task FetchGames_ActionAsync()", "Begin"));
            FetchGamesCommand<GamesList> FetchAllGames = new FetchGamesCommand<GamesList>();
            FetchAllGames.RequestFinished += new RequestFinishedEventHandler(FetchAllGames_Event);
            RestRequestAsyncHandle handle = FetchAllGames.ExcecuteAsyncCommand();
            Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Task FetchGames_ActionAsync()", "End"));
            //Workaround no return warning:
            return Task.Run(() => { });
        }

        private async void FetchAllGames_Event(object sender, RequestFinishedEventArgs e)
        {
            Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async void FetchAllGames_Event(object sender, RequestFinishedEventArgs e)", "Begin"));
            if (!e.Response.HasError())
            {
                GamesList gs = e.Response as GamesList;
                AllGamesBase.Clear();
                foreach (GameHeader g in gs.games)
                {
                    //Debug.WriteLine("id:" + g.Id + ", name:" + g.Name + ", owner:" + g.Owner + ", status:" + g.Status + ", time start:" + g.TimeStart + ", players count:" + g.PlayersCount + ", players max:" + g.PlayersMax);
                    AllGamesBase.Add(g);
                }
                await InitializeCollectionsAndBindAsync();
            }
            else
            {
                ServerResponse x = e.Response as ServerResponse;
                MessageBoxResult m = MessageBox.Show(x.error.ToString(), x.error_description.ToString(), MessageBoxButton.OK);
            }
            Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async void FetchAllGames_Event(object sender, RequestFinishedEventArgs e)", "End"));
        }

        private async Task FilterCollectionsAsync(String text, int index = 0)
        {
            switch (index)
            {
                case 0:
                    filteredCollection = await filter.ByOwnerAsync(MyGames, ApplicationSettings.Instance.LoggedUsername);
                    filteredCollection = await filter.ByNameAsync(filteredCollection, text);
                    Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task FilterCollectionsAsync()", "Collection for pivot: " + this.MainPivot.SelectedIndex + " with text: " + text + " has elements: " + filteredCollection.Count));
                    MyGamesListControl.ItemsSource = filteredCollection;
                    break;
                case 1:
                    List<double> myPosition = await Geo.GetPhoneLocationListAsync();
                    filteredCollection = await filter.ByDistanceAsync(NearestGames, myPosition, LOCATION_PROXIMITY);
                    filteredCollection = await filter.ByNameAsync(filteredCollection, text);
                    Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task FilterCollectionsAsync()", "Collection for pivot: " + this.MainPivot.SelectedIndex + " with text: " + text + " has elements: " + filteredCollection.Count));
                    NearestGamesListControl.ItemsSource = filteredCollection;
                    break;
                case 2:
                    filteredCollection = await filter.ByNameAsync(AllGames, text);
                    Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task FilterCollectionsAsync()", "Collection for pivot: " + this.MainPivot.SelectedIndex + " with text: " + text + " has elements: " + filteredCollection.Count));
                    AllGamesListControl.ItemsSource = filteredCollection;
                    break;
                case 3:
                    filteredCollection = await filter.ByStatusAsync(CompletedGames, "COMPLETED");
                    filteredCollection = await filter.ByNameAsync(filteredCollection, text);
                    Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "async Task FilterCollectionsAsync()", "Collection for pivot: " + this.MainPivot.SelectedIndex + " with text: " + text + " has elements: " + filteredCollection.Count));
                    CompletedGamesListControl.ItemsSource = filteredCollection;
                    break;
            }
        }

        private async Task FilterCollectionsAsync_Action()
        {
            String textBlock = FilterTextBox.Text;
            int index = this.MainPivot.SelectedIndex;
            String text = String.Empty;
            Debug.WriteLine(DebugInfo.LongFormat(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "FilterCollectionsAsync_Action()", "Displayed pivot item: " + index));
            if (pivotsText.TryGetValue(index, out text) && !text.Equals(textBlock))
            {
                pivotsText[index] = textBlock;
                await FilterCollectionsAsync(textBlock, index);
            }
        }

        #region page specific components and behavior

        private async void Refresh_ApplicationBarIconButton_Click(object sender, EventArgs e)
        {
            await FetchGames_ActionAsync();
        }

        protected async override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            if (ApplicationSettings.Instance.HasLoginInfo())
            {
                MainPivot.Title = String.Format(AppResources.ListGamesLoggedAs, ApplicationSettings.Instance.LoggedUsername);
            }
            await FetchAllGamesBaseAsync();
        }

        //TODO: cleanup
        private void GamesList_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            ListBox lb = sender as ListBox;
            System.Diagnostics.Debug.WriteLine("Selected index: " + lb.SelectedIndex);
            if (lb.SelectedIndex == -1)
                return;

            GameIdentificator gid = lb.Items.ElementAt(lb.SelectedIndex) as GameIdentificator;
            System.Diagnostics.Debug.WriteLine("Selected game id: " + gid.id);

            NavigationService.Navigate(new Uri(String.Format("/Pages/GameDetailsPage.xaml?gid={0}", gid.id), UriKind.Relative));

            // Reset selected index to -1 (no selection)
            lb.SelectedIndex = -1;
        }

        //TODO: cleanup
        private void Logout_ApplicationBarMenuItem_Click(object sender, EventArgs e)
        {
            LoginCommand Logger = new LoginCommand();
            if (Logger.LogOut())
                Debug.WriteLine("Logout SUCCESSFUL");
            else
                Debug.WriteLine("Logout FAILED");
            Debug.WriteLineIf(ApplicationSettings.Instance.LoggedUsername != null, "Logged as:" + ApplicationSettings.Instance.LoggedUsername);

            if (NavigationService.CanGoBack)
            {
                NavigationService.GoBack();
            }
            else
            {
                NavigationService.Navigate(new Uri("/Pages/MainPage.xaml", UriKind.Relative));
            }
        }

        private async void ThisPivot_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            await FilterCollectionsAsync_Action();
        }

        // Same functionality as private void ThisPivot_SelectionChanged(object sender, SelectionChangedEventArgs e)
        private async void FilterBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            await FilterCollectionsAsync_Action();
        }

        private void CreateGame_ApplicationBarIconButton_Click(object sender, EventArgs e)
        {
            NavigationService.Navigate(new Uri(String.Format("/Pages/CreateGamePage.xaml"), UriKind.Relative));
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

        protected void InitializeApplicationBar()
        {
            ApplicationBar = new ApplicationBar { IsVisible = true, IsMenuEnabled = true };

            var ApplicationBarButtonCreate = new ApplicationBarIconButton(new Uri("/Images/new.png", UriKind.Relative)) { Text = AppResources.ListGamesApplicationBarButtonCreate };
            ApplicationBarButtonCreate.Click += CreateGame_ApplicationBarIconButton_Click;
            ApplicationBar.Buttons.Add(ApplicationBarButtonCreate);

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
        #endregion
    }
}