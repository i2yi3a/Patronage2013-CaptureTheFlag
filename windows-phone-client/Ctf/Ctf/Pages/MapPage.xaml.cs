using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using Microsoft.Phone.Maps.Toolkit;
using System.Device.Location;
using Microsoft.Phone.Maps.Controls;
using Windows.Devices.Geolocation;
using System.Device.Location;
using Microsoft.Phone.Maps.Services;
using System.Windows.Shapes;
using System.Windows.Media;
using System.Windows.Input;

namespace Ctf
{

    public partial class MapPage : PhoneApplicationPage
    {
        MapLayer centerlayer;
        MapLayer redFlag;
        MapLayer blueFlag;
        



        public MapPage()
        {
            
            InitializeComponent();

        }


    private void MyMapCenter_Tap(object sender, System.Windows.Input.GestureEventArgs e)
    {
        if (centerlayer != null)
        {
            MyMap.Layers.Remove(centerlayer);
        }
        Point p = e. GetPosition(MyMap);
        GeoCoordinate s = MyMap.ConvertViewportPointToGeoCoordinate(p);

        Ellipse myCircle = new Ellipse();
        myCircle.Fill = new SolidColorBrush(Colors.Green);
        myCircle.Height = 20;
        myCircle.Width = 20;
        myCircle.Opacity = 50;

        MapOverlay myLocationOverlay = new MapOverlay();
        myLocationOverlay.Content = myCircle;
        myLocationOverlay.PositionOrigin = new Point(0, 0);
        myLocationOverlay.GeoCoordinate = s;
        center.Text = s.ToString();

        centerlayer= new MapLayer();
        centerlayer.Add(myLocationOverlay);

        MyMap.Layers.Add(centerlayer);
        ApplicationBarIconButton b = (ApplicationBarIconButton)ApplicationBar.Buttons[1];
        b.IsEnabled = true;
       
        
    }

    private void MyMapRed_Tap(object sender, System.Windows.Input.GestureEventArgs e)
    {
        if (blueFlag != null)
        {
            MyMap.Layers.Remove(redFlag);
        }
        Point p = e.GetPosition(MyMap);
        GeoCoordinate s = MyMap.ConvertViewportPointToGeoCoordinate(p);

        Ellipse myCircle = new Ellipse();
        myCircle.Fill = new SolidColorBrush(Colors.Red);
        myCircle.Height = 20;
        myCircle.Width = 20;
        myCircle.Opacity = 50;

        MapOverlay myLocationOverlay = new MapOverlay();
        myLocationOverlay.Content = myCircle;
        myLocationOverlay.PositionOrigin = new Point(0, 0);
        myLocationOverlay.GeoCoordinate = s;
        redflag.Text = s.ToString()  ;

        redFlag = new MapLayer();
        redFlag.Add(myLocationOverlay);

        MyMap.Layers.Add(redFlag);
        ApplicationBarIconButton b = (ApplicationBarIconButton)ApplicationBar.Buttons[2];
        b.IsEnabled = true;
    }
    private void MyMapBlue_Tap(object sender, System.Windows.Input.GestureEventArgs e)
    {
        if (blueFlag != null)
        {
            MyMap.Layers.Remove(blueFlag);
        }
        Point p = e.GetPosition(MyMap);
        GeoCoordinate s = MyMap.ConvertViewportPointToGeoCoordinate(p);

        Ellipse myCircle = new Ellipse();
        myCircle.Fill = new SolidColorBrush(Colors.Blue);
        myCircle.Height = 20;
        myCircle.Width = 20;
        myCircle.Opacity = 50;

        MapOverlay myLocationOverlay = new MapOverlay();
        myLocationOverlay.Content = myCircle;
        myLocationOverlay.PositionOrigin = new Point(0, 0);
        myLocationOverlay.GeoCoordinate = s;
        blueflag.Text = s.ToString();

        blueFlag = new MapLayer();
        blueFlag.Add(myLocationOverlay);

        MyMap.Layers.Add(blueFlag);
        ApplicationBarIconButton b = (ApplicationBarIconButton)ApplicationBar.Buttons[3];
        b.IsEnabled = true;
        
    }
    private void ApplicationBarIconButton_Click_1(object sender, EventArgs e)
    {
        MyMap.Tap -= MyMapBlue_Tap;
        MyMap.Tap -= MyMapRed_Tap;
        MyMap.Tap += MyMapCenter_Tap;

    }

    private void ApplicationBarIconButton_Click_2(object sender, EventArgs e)
    {
        MyMap.Tap -= MyMapBlue_Tap;
        MyMap.Tap -= MyMapCenter_Tap;
        MyMap.Tap += MyMapRed_Tap;
    }

    private void ApplicationBarIconButton_Click_3(object sender, EventArgs e)
    {
        MyMap.Tap -= MyMapRed_Tap;
        MyMap.Tap -= MyMapCenter_Tap;
        MyMap.Tap += MyMapBlue_Tap;
    }

    private void okAppBarButton_Click(object sender, EventArgs e)
    {
        string uri = string.Format("/Pages/CreateGamePage.xaml?parametr1="+ center.Text+ "&parametr2="+redflag.Text+ "&parametr3=" +blueflag.Text);

        NavigationService.Navigate(new Uri(uri, UriKind.Relative));
    }


    }
}