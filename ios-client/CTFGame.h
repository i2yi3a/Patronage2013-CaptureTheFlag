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

@property (strong, nonatomic) NSString *name;
@property (strong, nonatomic) NSString *gameDescription;
@property (strong, nonatomic) NSDate *date;
@property (strong, nonatomic) NSNumber *time;
@property (strong, nonatomic) NSNumber *duration;
@property (strong, nonatomic) NSNumber *points_max;
@property (strong, nonatomic) NSNumber *players_max;
@property (strong, nonatomic) NSString *localization_name;
@property (strong, nonatomic) CLLocation *localizationLatLng;
@property (strong, nonatomic) NSNumber *localizationRadius;

@end
