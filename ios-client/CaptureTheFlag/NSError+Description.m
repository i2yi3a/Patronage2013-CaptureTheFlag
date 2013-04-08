//
//  NSError+Description.m
//  CaptureTheFlag
//
//  Created by Sebastian Jędruszkiewicz on 4/4/13.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "NSError+Description.h"

@implementation NSError (Description)

+ (NSError*)errorWithDescription:(NSString*)description
{
    NSMutableDictionary *errorDetail = [NSMutableDictionary dictionary];
    [errorDetail setValue:description forKey:NSLocalizedDescriptionKey];
    NSError* error = [NSError errorWithDomain:@"CaptureTheFlag" code:1 userInfo:errorDetail];
    return error;
}

@end
