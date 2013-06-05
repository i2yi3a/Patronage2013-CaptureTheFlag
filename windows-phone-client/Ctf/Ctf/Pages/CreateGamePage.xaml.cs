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
            CreateCommand CreateGame = new CreateCommand();
            CreateGame.RequestFinished += new RequestFinishedEventHandler(CreateGame_RequestFinished);
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
    }
}