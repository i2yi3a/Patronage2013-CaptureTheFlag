//
//  CTFGame.h
//  CaptureTheFlag
//
//  Created by Milena Gnoińska on 28.04.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>
#import "NSDate+RFC1123.h"

@interface CTFGame : NSObject


@property (strong, nonatomic) NSString *name;
@property (strong, nonatomic) NSString *gameDescription;
@property (strong, nonatomic) NSDate *timeStart;
@property (strong, nonatomic) NSNumber *duration;
@property (strong, nonatomic) NSNumber *pointsMax;
@property (strong, nonatomic) NSNumber *playersMax;
@property (strong, nonatomic) NSString *localizationName;
@property (strong, nonatomic) CLLocation *localizationLat;
@property (strong, nonatomic) CLLocation *localizationLng;
@property (strong, nonatomic) NSNumber *localizationRadius;

@end
