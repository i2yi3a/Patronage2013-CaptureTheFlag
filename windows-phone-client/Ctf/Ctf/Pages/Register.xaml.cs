using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using System.Diagnostics;



namespace Ctf
{
    public partial class Register : PhoneApplicationPage
    {
        public Register()
        {
            InitializeComponent();
            registerButton.IsEnabled = false;
            
        }

        private void txtChanged(object sender, RoutedEventArgs e)
        {

            if (UserCredentials.IsUsernameProperLength(userNameRegister.Text) && UserCredentials.IsPasswordProperLength(passwordRegister1.Password) && UserCredentials.IsPasswordProperLength(passwordRegister2.Password))
            {
                registerButton.IsEnabled = true;
            }
            else
            {
                registerButton.IsEnabled = false;
            }
        }
        

        private async void Button_Click_1(object sender, RoutedEventArgs e)
        {
            waitIndicator.Visibility = Visibility.Visible;
            Registration Registers = new Registration();
            await Registers.Register(new UserCredentials(userNameRegister.Text, passwordRegister1.Password), passwordRegister2.Password);
            Registers.MessengerSent += Registers_MessengerSent;
            
        }

        private void Registers_MessengerSent(object sender, EventArgs e)
        {
            MessengerSentEventArgs x;
            x = (MessengerSentEventArgs) e;
            MessageBoxResult m = MessageBox.Show(x.message.ToString(), x.errorCode.ToString(), MessageBoxButton.OK);
            if (m == MessageBoxResult.OK)
            { waitIndicator.Visibility = Visibility.Collapsed; }
            if (x.errorCode == 0)
            {
                NavigationService.GoBack();
            }

        }



    }
}