using Ctf.ApplicationTools;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Models
{
    //public delegate void PropertyChangedEventHandler(object sender, PropertyChangedEventArgs e);

    public class BindableBase : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        protected virtual void RaisePropertyChanged(string propertyName)
        {
            PropertyChangedEventHandler handler = this.PropertyChanged;
            if (handler != null)
            {
                Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Property changed: " + propertyName));
                handler(this, new PropertyChangedEventArgs(propertyName));
            }
        }
    }
}
