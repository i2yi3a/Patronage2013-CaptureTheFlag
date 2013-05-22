using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Models
{
    public class GameHeader : BaseModel
    {
        private string id;
        private string name;
        private string status;
        private string owner;

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
    }
}
