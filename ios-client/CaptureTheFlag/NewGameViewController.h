//
//  NewGameViewController.h
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 29.04.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <UIKit/UIKit.h>
#import<MapKit/MapKit.h>

@interface NewGameViewController : UIViewController <MKMapViewDelegate,UIPickerViewDataSource,UIPickerViewDelegate> {
    IBOutlet UIScrollView * Scroller;
}
- (IBAction)geolocate:(id)sender;
- (IBAction)createNewGame:(id)sender;


@property (strong, nonatomic) IBOutlet UITextField *locationField;
@property (weak, nonatomic) IBOutlet MKMapView *mapView;
@property (strong, nonatomic) CLGeocoder *geocoder;
@property (strong, nonatomic) NSArray *oneColumnList;
@property (strong, nonatomic) NSArray *secondColumnList;

@end