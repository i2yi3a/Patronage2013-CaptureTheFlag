//
//  NewGameViewController.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 29.04.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "NewGameViewController.h"

@interface NewGameViewController ()
@property (weak, nonatomic) IBOutlet UITextField *gameName;
@property (weak, nonatomic) IBOutlet UITextField *gameDescription;
@property (weak, nonatomic) IBOutlet UIDatePicker *gameStart;
@property (weak, nonatomic) IBOutlet UIDatePicker *gameDuration;
@property (weak, nonatomic) IBOutlet UIPickerView *gamePicker;



@end

@implementation NewGameViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    //picker
    _gamePicker.showsSelectionIndicator = TRUE;
    self.oneColumnList=[[NSArray alloc] initWithObjects:@"2",@"3",@"4",@"5",@"6",@"7",@"8",@"9",@"10",@"11",@"12", nil];
    self.secondColumnList=[[NSArray alloc] initWithObjects:@"5", @"10",@"15",@"20",@"25",@"30", @"40",@"50", nil];
    //map
    _mapView.showsUserLocation = YES;
    _mapView.delegate = self;
    self.geocoder = [[CLGeocoder alloc] init];
    
        //scrolling
    [Scroller setScrollEnabled:YES];
    [Scroller setContentSize:CGSizeMake(320, 1300)];
    //dissmising keybord after return
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc]
                                   initWithTarget:self
                                   action:@selector(dismissKeyboard)];
     [self.view addGestureRecognizer:tap];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)geolocate:(id)sender
{
    [self.geocoder geocodeAddressString:self.locationField.text
                      completionHandler:^(NSArray *coordinates, NSError
                                          *error) {
                          if (coordinates.count)
                          {
                              CLPlacemark *placemark = coordinates[0];
                              CLLocation *coordinate = placemark.location;
                              MKCircle *circle = [MKCircle circleWithCenterCoordinate:coordinate.coordinate radius:500];
                              [_mapView addOverlay:circle];
                              
                              MKPointAnnotation *annotationPoint = [[MKPointAnnotation alloc] init];
                              annotationPoint.coordinate = coordinate.coordinate;
                              annotationPoint.title = @"placemark.name";
                              [_mapView addAnnotation:annotationPoint];
                              MKCoordinateRegion region =
                              MKCoordinateRegionMakeWithDistance (
                                                                  coordinate.coordinate, 500, 500);
                              [_mapView setRegion:region animated:YES];
                          }
                          else
                          {
                              //error
                          }
                      }];
}
- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    if (textField == self.gameName) {
        [textField resignFirstResponder];
        [self.gameDescription becomeFirstResponder];
    }
    else if (textField == self.gameDescription) {
        [textField resignFirstResponder];
        [self.locationField becomeFirstResponder];
    }
    else if (textField == self.locationField) {
        [textField resignFirstResponder];
    }

    return YES;
}

-(void)dismissKeyboard {
    [_gameName resignFirstResponder];
    [_gameDescription resignFirstResponder];
    [_locationField resignFirstResponder];
}

- (void)mapView:(MKMapView *)mapView
didUpdateUserLocation:
(MKUserLocation *)userLocation
{
   // _mapView.centerCoordinate =
   // userLocation.location.coordinate;
}

- (MKAnnotationView *) mapView:(MKMapView *)mapView viewForAnnotation:(id <MKAnnotation>) annotation
{
    if ([[annotation title] isEqualToString:@"Current Location"]) {
        return nil;
    }
    
    MKAnnotationView *annView = [[MKAnnotationView alloc ] initWithAnnotation:annotation reuseIdentifier:@"currentloc"];
    if ([[annotation title] isEqualToString:@"placemark.name"])
        annView.image = [ UIImage imageNamed:@"BLstream.png" ];
    else
        annView.image = [ UIImage imageNamed:@"pinRed.png" ];
    UIButton *infoButton = [UIButton buttonWithType:UIButtonTypeDetailDisclosure];
    [infoButton addTarget:self action:@selector(showDetailsView)
         forControlEvents:UIControlEventTouchUpInside];
    annView.canShowCallout = YES;
    return annView;
}

-(MKOverlayView *)mapView:(MKMapView *)mapView viewForOverlay:(id)overlay
{
    MKCircleView *circleView = [[MKCircleView alloc] initWithOverlay:overlay];
    circleView.strokeColor = [UIColor blueColor];
    circleView.lineWidth = 2;
    return circleView;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    if (component == 0) {
        return [_oneColumnList count];
    }
    
    return [_secondColumnList count];
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 2;
}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row
            forComponent:(NSInteger)component
{
    if (component == 0) {
        return [_oneColumnList objectAtIndex:row];
        
    }
    
    return [_secondColumnList objectAtIndex:row];
    
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    if (component == 0) {
                return;
    }
    
    
}
@end
