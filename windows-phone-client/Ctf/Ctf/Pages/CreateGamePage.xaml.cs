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
            CreateGame.CreateGame(new Game(CGNameBox.Text, CGDescriptionBox.Text, CGTime_StartBox.Text, Convert.ToInt32(CGDurationBox.Text), int.Parse(CGPoints_maxBox.Text),int.Parse(CGPlayers_maxBox.Text), new Localization(CGLocalizationNameBox.Text,
                new List<double> { Double.Parse(CGLocalizationLatLngBox.Text), Double.Parse(CGLocalizationLatLngBox.Text) }, int.Parse(CGLocalizationRadiusBox.Text)),
                new TeamBase(CGRedNameBox.Text, new List<double> { Double.Parse(CGRedLatLng.Text), Double.Parse(CGRedLatLng.Text) }),
                new TeamBase(CGBlueNameBox.Text, new List<double> { Double.Parse(CGBlueLatLngBox.Text), Double.Parse(CGBlueLatLngBox.Text) })));

        }
    }
}