//
//  NewGame5ViewController.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 21.06.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "NewGame5ViewController.h"
#import "NewGame1ViewController.h"
#import "CTFGame.h"
#import <AddressBookUI/AddressBookUI.h>

@interface NewGame5ViewController ()
@property (weak, nonatomic) IBOutlet FlatButton *backButton;
@property (weak, nonatomic) IBOutlet FlatButton *nextButton;
@property (weak, nonatomic) IBOutlet FlatButton *geoLocateButton;
@property (weak, nonatomic) IBOutlet UIView *locationFieldBackgroundView;
@property (weak, nonatomic) IBOutlet UIView *topBar;
@property (strong, nonatomic) NSNumber * gameRadius;
@property (strong, nonatomic) CLLocation *gameLocation;
@property (strong, nonatomic) NSString *addres;
@property (strong, nonatomic) CLLocation *redTeamBaseLocalization;
@property (strong, nonatomic) CLLocation *blueTeamBaseLocalization;
@property (strong, nonatomic) IBOutlet UITextField *locationField;
@property (weak, nonatomic) IBOutlet MKMapView *mapView;
@property (strong, nonatomic) CLGeocoder *geocoder;
@property (weak, nonatomic) IBOutlet UIView *MapHideView;

- (IBAction)geolocate:(id)sender;
- (IBAction)createNewGame:(id)sender;
- (IBAction)goToCreatingNewGame4:(id)sender;
@end

@implementation NewGame5ViewController
int counter;

- (IBAction)goToCreatingNewGame4:(id)sender{
    [self.navigationController popViewControllerAnimated:YES];
}



- (void)viewDidLoad
{
    [super viewDidLoad];
    _MapHideView.alpha=1;
    _MapHideView.backgroundColor=[UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _mapView.showsUserLocation = YES;
    _mapView.delegate = self;
    self.geocoder = [[CLGeocoder alloc] init];
    
    UITapGestureRecognizer *tapRecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(onTap:)];
    
    tapRecognizer.numberOfTapsRequired = 1;
    
    tapRecognizer.numberOfTouchesRequired = 1;
    
    [self.mapView addGestureRecognizer:tapRecognizer];
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc]
                                   initWithTarget:self
                                   action:@selector(dismissKeyboard)];
    [self.view addGestureRecognizer:tap];


    self.view.backgroundColor = [UIColor ctfApplicationBackgroundLighterColor];
    
    _geoLocateButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _geoLocateButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];

    
    _backButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _backButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
    _nextButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _nextButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
    _topBar.backgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    _locationFieldBackgroundView.backgroundColor = [UIColor ctfInputBackgroundAndDisabledButtonColor];

	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)mapView:(MKMapView *)mapView1 regionDidChangeAnimated:(BOOL)animated
{
    if (![self.locationField.text isEqualToString:@""]) {
        [self.mapView removeOverlays:self.mapView.overlays];
        [self removeAllAnnotations];
        MKCoordinateSpan span = _mapView.region.span;
        CLLocationCoordinate2D centre = [_mapView centerCoordinate];
        MKCircle *circle = [MKCircle circleWithCenterCoordinate:centre radius:span.latitudeDelta*111*1000/2];
        self.gameRadius = [NSNumber numberWithDouble:(circle.radius)];
        [_mapView addOverlay:circle];
        
        MKPointAnnotation *annotationPoint = [[MKPointAnnotation alloc] init];
        annotationPoint.coordinate = centre;
        annotationPoint.title = @"center"; 
        [_mapView addAnnotation:annotationPoint];
        MKCoordinateRegion region =
        MKCoordinateRegionMakeWithDistance(centre, span.latitudeDelta*111*1000, span.latitudeDelta*111*1000);
        [_mapView setRegion:region animated:YES];
        counter=0;
    }
    else
    {
        CLLocationCoordinate2D centerStartingCoordinates;
        centerStartingCoordinates.latitude=37.178181;
        centerStartingCoordinates.longitude=-96.054581;
        MKCoordinateRegion region =
        MKCoordinateRegionMakeWithDistance (
                                            centerStartingCoordinates , 2500000, 2500000);
        [_mapView setRegion:region animated:NO];
    }
}

- (IBAction)geolocate:(id)sender
{
    if ([_locationField.text isEqualToString:@""]){
        [ShowInformation showError:@"Fill empty field"];
    }
    else{
     _MapHideView.alpha=0;
    [self.mapView removeOverlays:self.mapView.overlays];
    [self removeAllAnnotations];
    [self.geocoder geocodeAddressString:self.locationField.text
                      completionHandler:^(NSArray *coordinates, NSError
                                          *error) {
                          if (coordinates.count)
                          {
                              CLPlacemark *placemark = coordinates[0];
                              CLLocation *coordinate = placemark.location;
                              self.gameLocation = [[CLLocation alloc] initWithLatitude:coordinate.coordinate.latitude
                                                                             longitude:coordinate.coordinate.longitude];
                              MKCircle *circle = [MKCircle circleWithCenterCoordinate:coordinate.coordinate radius:450];
                              [_mapView addOverlay:circle];
                              
                              MKPointAnnotation *annotationPoint = [[MKPointAnnotation alloc] init];
                              annotationPoint.coordinate = coordinate.coordinate;
                              annotationPoint.title = @"center"; //??
                              [_mapView addAnnotation:annotationPoint];
                              MKCoordinateRegion region =
                              MKCoordinateRegionMakeWithDistance (
                                                                  coordinate.coordinate, 800, 800);
                              [_mapView setRegion:region animated:YES];
                              NSDictionary *addressDictionary = placemark.addressDictionary;
                              NSString* address =
                              ABCreateStringWithAddressDictionary(addressDictionary, NO);
                              self.addres = [address
                                             stringByReplacingOccurrencesOfString:@"\n" withString:@" "];
                              counter=0;
                          }
                          else
                          {
                              //error
                          }
                      }];
}
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
    if ([[annotation title] isEqualToString:@"center"])
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
    circleView.fillColor = [UIColor colorWithRed:0.0 green:0.0 blue:1.0 alpha:0.2];
    return circleView;
}

-(void)removeAllAnnotations
{
    id userAnnotation = self.mapView.userLocation;
    
    NSMutableArray *annotations = [NSMutableArray arrayWithArray:self.mapView.annotations];
    [annotations removeObject:userAnnotation];
    
    [self.mapView removeAnnotations:annotations];
}


-(void)onTap:(UITapGestureRecognizer *)recognizer
{
    counter +=1;
    CGPoint point = [recognizer locationInView:self.mapView];
    
    CLLocationCoordinate2D coordinate = [self.mapView convertPoint:point toCoordinateFromView:self.mapView];
    
    if ((counter % 3)==1) {
                _mapView.scrollEnabled = NO;
        MKPointAnnotation *annotationPoint = [[MKPointAnnotation alloc] init];
        annotationPoint.coordinate = coordinate;
        self.redTeamBaseLocalization = [[CLLocation alloc] initWithLatitude:annotationPoint.coordinate.latitude
                                                                  longitude:annotationPoint.coordinate.longitude];
        annotationPoint.title = @"RED";
        [_mapView addAnnotation:annotationPoint];

    }
    
    if ((counter % 3)==2) {
                _mapView.scrollEnabled = NO;
        MKPointAnnotation *annotationPoint = [[MKPointAnnotation alloc] init];
        annotationPoint.coordinate = coordinate;
        self.blueTeamBaseLocalization = [[CLLocation alloc] initWithLatitude:annotationPoint.coordinate.latitude
                                                                   longitude:annotationPoint.coordinate.longitude];
        annotationPoint.title = @"BLUE";
        [_mapView addAnnotation:annotationPoint];
    }
    if ((counter % 3)==0)
    {
                _mapView.scrollEnabled = YES;
        id userAnnotation = self.mapView.userLocation;
        NSMutableArray *annotations = [NSMutableArray arrayWithArray:self.mapView.annotations];
        [annotations removeObject:userAnnotation];
        [self.mapView removeAnnotations:annotations];
        [self geolocate:nil];
        
    }
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    if (textField == self.locationField) {
        [textField resignFirstResponder];
        [self geolocate:nil];
    }
    return YES;
}

- (IBAction)createNewGame:(id)sender{
    if (_blueTeamBaseLocalization!=nil) {
        
    
    
    _game.localizationName = _addres;
    _game.localization = _gameLocation;
    _game.localizationRadius = _gameRadius;
    _game.redTeamBaseLocalization = _redTeamBaseLocalization;
    _game.blueTeamBaseLocalization =_blueTeamBaseLocalization;
    
    [[NetworkEngine getInstance] createNewGame:_game completionBlock:^(NSObject *response){
        if ([response isKindOfClass:[NSError class]])
        {
            NSError *error = (NSError *)response;
            [ShowInformation showError:error.localizedDescription];
        }
        else
        {
            [ShowInformation showMessage:@"Congratulations" withTitle:@"Game created sucesfully!"];
        }
        
    }];
    }
    {
       [ShowInformation showError:@"Some informations are not given"];
    }
}
@end
