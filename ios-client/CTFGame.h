//
//  CTFGame.h
//  CaptureTheFlag
//
//  Created by Milena Gnoińska on 28.04.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>

@interface CTFGame : NSObject

@property (weak, nonatomic) NSString *name;
@property (weak, nonatomic) NSString *description;
@property (weak, nonatomic) NSDate *date;
@property (weak, nonatomic) NSNumber *time;
@property (weak, nonatomic) NSNumber *duration;
@property (weak, nonatomic) NSNumber *points_max;
@property (weak, nonatomic) NSNumber *points_min;
@property (weak, nonatomic) NSNumber *players_max;
@property (weak, nonatomic) NSString *localization_name;
@property (weak, nonatomic) CLLocation *localization_latLng;
@property (weak, nonatomic) NSNumber *localization_radius;

@end
