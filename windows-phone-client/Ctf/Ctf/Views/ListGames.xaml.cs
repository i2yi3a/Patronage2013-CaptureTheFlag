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

namespace Ctf.Views
{
    public partial class ListGames : PhoneApplicationPage
    {
        public ListGames()
        {
            InitializeComponent();

            this.DataContext = new ListGamesViewModel(LayoutRoot);
        }
    }
}