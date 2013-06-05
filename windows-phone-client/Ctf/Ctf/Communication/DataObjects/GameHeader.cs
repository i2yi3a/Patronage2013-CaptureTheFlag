using Ctf.Communication.DataObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Models.DataObjects
{
    public class GameHeader : GameIdentificator
    {
        private string name;
        public Localization localization { get; set; }
        private string status;
        private string owner;
        public string time_start;
        public int players_max;
        public int players_count;

        public GameHeader() : base()
        {
        }

        public GameHeader(string name, string status, string owner)
        {
            this.name = name;
            this.status = status;
            this.owner = owner;
        }

        public string Id 
        {
            get 
            {
                return this.id;
            }
            set
            {
                if (this.id != value)
                {
                    this.id = value;
                    this.RaisePropertyChanged("Id");
                }
            }
        }

        public string Name 
        {
            get
            {
                return this.name;
            }
            set
            {
                if (this.name != value)
                {
                    this.name = value;
                    this.RaisePropertyChanged("Name");
                }
            }
        }

        public string Status
        {
            get
            {
                return this.status;
            }
            set
            {
                if (this.status != value)
                {
                    this.status = value;
                    this.RaisePropertyChanged("Status");
                }
            }
        }

        public string Owner 
        {
            get
            {
                return this.owner;
            }
            set
            {
                if (this.owner != value)
                {
                    this.owner = value;
                    this.RaisePropertyChanged("Owner");
                }
            }
        }

        public string TimeStart
        {
            get
            {
                return this.time_start;
            }
            set
            {
                if (this.time_start != value)
                {
                    this.time_start = value;
                    this.RaisePropertyChanged("TimeStart");
                }
            }
        }

        public int PlayersCount
        {
            get
            {
                return this.players_count;
            }
            set
            {
                if (this.players_count != value)
                {
                    this.players_count = value;
                    this.RaisePropertyChanged("PlayersCount");
                }
            }
        }

        public int PlayersMax
        {
            get
            {
                return this.players_max;
            }
            set
            {
                if (this.players_max != value)
                {
                    this.players_max = value;
                    this.RaisePropertyChanged("PlayersMax");
                }
            }
        }
    }
}
