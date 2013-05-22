using Ctf.Communication;
using Ctf.Communication.DataObjects;
using Ctf.Models;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace Ctf.ViewModels
{
    public class ListGamesViewModel : BaseViewModel
    {
        private ObservableCollection<GameHeader> gamesDataSource;
        private ICommand fetchGamesCommand;
        private GameHeader selectedGame;
        private FetchGamesCommand fetchGamesCmd;
        private System.Windows.Controls.Grid LayoutRoot;

        public ListGamesViewModel()
        {
            this.fetchGamesCmd = new FetchGamesCommand();
            this.fetchGamesCommand = new DelegateCommand(this.FetchGamesAction);
            fetchGamesCmd.RequestFinished += new RequestFinishedEventHandler(FinishedRequest);
        }

        public ListGamesViewModel(System.Windows.Controls.Grid LayoutRoot) : this()
        {
            this.LayoutRoot = LayoutRoot;
        }

        // Back clears list, how to cache it ?
        public void FinishedRequest(object sender, RequestFinishedEventArgs e)
        {
            if (e.Response.HasError())
            {
                ServerJsonResponse s = e.Response as ServerJsonResponse;
            }
            else
            {
                JsonGames g = e.Response as JsonGames;
                foreach (GameHeader gameHeader in g.games)
                {
                    GamesDataSource.Add(gameHeader);
                }
                this.RaisePropertyChanged("GamesDataSource");
                
            }
            //show button  in can execute!
        }

        private void FetchGamesAction(object parameter)
        {
            var button = LayoutRoot.FindName("FetchButton");
            //hide button in can execute!
            fetchGamesCmd.FetchGames();
        }

        public ICommand FetchGamesCommand
        {
            get
            {
                return this.fetchGamesCommand;
            }
        }

        public ObservableCollection<GameHeader> GamesDataSource
        {
            get
            {
                if (this.gamesDataSource == null)
                {
                    this.gamesDataSource = new ObservableCollection<GameHeader>();
                }
                return this.gamesDataSource;
            }
        }

        public GameHeader SelectedGame
        {
            get
            {
                return this.selectedGame;
            }
            set
            {
                if (this.selectedGame != value)
                {
                    this.selectedGame = value;
                    //if (this.selectedGame != null)
                    //{
                    //    this.name = this.selectedGame.Name;
                    //    this.age = this.selectedGame.Age;
                    //}
                    //this.RaisePropertyChanged("SelectedName");
                    //this.RaisePropertyChanged("SelectedAge");
                }
            }
        }
    }
}
