using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using Ctf.Communication;
using Ctf.ApplicationTools.DataObjects;
using Ctf.Communication.DataObjects;
using System.Diagnostics;

namespace Ctf.Pages
{
    public partial class CreateGamePage : PhoneApplicationPage
    {
        public CreateGamePage()
        {
            InitializeComponent();
            CreateGameButton.IsEnabled = false;
            Date_Picker.ValueStringFormat = Date_Picker.Value.Value.Day.ToString() + "-" + Date_Picker.Value.Value.Month.ToString() + "-" + Date_Picker.Value.Value.Year.ToString();


        }

        private void CreateGame_ApplicationBarIconButton_Click(object sender, EventArgs e)
        {
            //CreateCommand CreateGame = new CreateCommand();
            //CreateGame.RequestFinished += new RequestFinishedEventHandler(CreateGame_RequestFinished);
            //CreateGame.CreateGame(new Game("zzzzz", "zzzzzz", "01-06-2013 10:13:00", 5400000, 12, 10, new Localization("Warsaw, Polska", new List<double> { 15, 12 }, 1500)
            //    , new TeamBase("kociaki", new List<double> { 20, 15 }), new TeamBase("miczikoty", new List<double> { 35, 14 })));
            //gaame.CreateGame(new Game("sproba", "jasna", "10-06-2013 10:13:00", 5400000, 12, 10, new Localization("Jasne błonia, Szczecin, Polska", new LatLng(15, 15), 1500)));
        }


        void CreateGame_RequestFinished(object sender, RequestFinishedEventArgs e)
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

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            CreateCommand CreateGame = new CreateCommand();
            CreateGame.RequestFinished += new RequestFinishedEventHandler(CreateGame_RequestFinished);
            //CreateGame.CreateGame(new Game("Politika", "krotki opis", "12-06-2013 10:13:00", 5400000, 12, 10, new Localization("Warsaw, Polska", new List<double> {15, 12 } , 1500), new TeamBase("kociaki", new List<double> { 20.00, 15.00 }), new TeamBase("miczikoty", new List<double> { 35.00, 14.00 })));

            var yArray = CGRedLatLng.Text.Split(',').Select(m => Double.Parse(m.Trim())).ToArray();
            var xArray = CGBlueLatLngBox.Text.Split(',').Select(m => Double.Parse(m.Trim())).ToArray();
            var zArray = CGLocalizationLatLngBox.Text.Split(',').Select(m => Double.Parse(m.Trim())).ToArray();

            CreateGame.CreateGame(new Game(CGNameBox.Text, CGDescriptionBox.Text, Date_Picker.ValueString + " " + TimeePicker.ValueString, Convert.ToInt32(CGDurationBox.Text),
                int.Parse(CGPoints_maxBox.Text), int.Parse(CGPlayers_maxBox.Text), new Localization(CGLocalizationNameBox.Text,
                new List<double>(zArray), int.Parse(CGLocalizationRadiusBox.Text)),
                new TeamBase(CGRedNameBox.Text, new List<double>(yArray)),
                new TeamBase(CGBlueNameBox.Text, new List<double>(xArray))));

        }
        private void DatePicker_ValueChanged(object sender, DateTimeValueChangedEventArgs e)
        {
            Date_Picker.ValueStringFormat = Date_Picker.Value.Value.Day.ToString() + "-" + Date_Picker.Value.Value.Month.ToString() + "-" + Date_Picker.Value.Value.Year.ToString();
        }

        private void TimePicker_ValueChanged(object sender, DateTimeValueChangedEventArgs e)
        {
        }

        private void Button_Click_2(object sender, RoutedEventArgs e)
        {
            NavigationService.Navigate(new Uri(String.Format("/Pages/MapPage.xaml"), UriKind.Relative));
        }


        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            var uri = NavigationService.CurrentSource;
            Debug.WriteLine(uri);
            if (!uri.ToString().Equals("/Pages/CreateGamePage.xaml"))
            {
                string x = NavigationContext.QueryString["parametr1"];
                Debug.WriteLine(x);
                CGLocalizationLatLngBox.Text = x;

                CGRedLatLng.Text = NavigationContext.QueryString["parametr2"];
                CGBlueLatLngBox.Text = NavigationContext.QueryString["parametr3"];
                CGLocalizationRadiusBox.Text = NavigationContext.QueryString["radius"];

            }
        }


        private void RequiredBoxes_Changed(object sender, RoutedEventArgs e)
        {
            String errorInfo = String.Empty;
            CreateGameButton.IsEnabled = false;
            if (!String.IsNullOrWhiteSpace(CGNameBox.Text) && !String.IsNullOrWhiteSpace(CGDescriptionBox.Text) && !String.IsNullOrWhiteSpace(CGDurationBox.Text) &&
                !String.IsNullOrWhiteSpace(CGPoints_maxBox.Text) && !String.IsNullOrWhiteSpace(CGPlayers_maxBox.Text) && !String.IsNullOrWhiteSpace(CGLocalizationNameBox.Text) &&
                !String.IsNullOrWhiteSpace(CGLocalizationLatLngBox.Text) && !String.IsNullOrWhiteSpace(CGLocalizationRadiusBox.Text) && !String.IsNullOrWhiteSpace(CGRedNameBox.Text) &&
                !String.IsNullOrWhiteSpace(CGRedLatLng.Text) && !String.IsNullOrWhiteSpace(CGBlueNameBox.Text) && !String.IsNullOrWhiteSpace(CGBlueLatLngBox.Text))
            {
                CreateGameButton.IsEnabled = true;
            }

        }

        private void CGPoints_maxBox_KeyDown(object sender, System.Windows.Input.KeyEventArgs e)
        {
            if (System.Text.RegularExpressions.Regex.IsMatch(e.Key.ToString(), "[0-9]"))
                e.Handled = false;
            else e.Handled = true;
        }
    }

}