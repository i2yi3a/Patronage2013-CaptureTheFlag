//
//  NSError+Description.h
//  CaptureTheFlag
//
//  Created by Sebastian Jędruszkiewicz on 4/4/13.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSError (Description)

+ (NSError*)errorWithDescription:(NSString*)description;

@end
