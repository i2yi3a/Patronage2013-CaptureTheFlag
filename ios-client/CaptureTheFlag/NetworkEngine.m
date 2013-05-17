//
//  NetworkEngine.m
//  CaptureTheFlag
//
//  Created by Sebastian Jędruszkiewicz on 4/4/13.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <CoreLocation/CoreLocation.h>

#import "NetworkEngine.h"
#import "MKNetworkKit.h"
#import "CTFGame.h"

#define CTF_SERVER @"capturetheflag.blstream.com"
#define CTF_API_PATH @"demo"
#define CTF_API_CLIENT_SECRET @"secret"
#define CTF_API_CLIENT_ID @"mobile_ios"
#define CTF_API_GRANT_TYPE @"password"
#define CTF_SERVER_PORT 18080

@interface NetworkEngine()

@property (nonatomic, strong) NSString* token;

@end

@implementation NetworkEngine

+ (NetworkEngine *)getInstance
{
	static NetworkEngine *neInstance;
	
	@synchronized(self)
	{
		if (!neInstance)
		{
			neInstance = [[NetworkEngine alloc] initWithHostName:CTF_SERVER
                                                             apiPath:CTF_API_PATH
                                                  customHeaderFields:@{@"Accept-Encoding" : @"gzip"}];
            neInstance.portNumber = CTF_SERVER_PORT;
		}
		return neInstance;
	}
}

- (void)registerUser:(NSString *)email
        withPassword:(NSString *)password
     completionBlock:(NetworkEngineCompletionBlock)completionBlock
{
    MKNetworkOperation *op = [self operationWithPath:@"api/players/add"
                                              params:@{@"username" : email, @"password" : password}
                                          httpMethod:@"POST"
                                                 ssl:NO];

    [op setPostDataEncoding:MKNKPostDataEncodingTypeJSON];
    
    [op addCompletionHandler:^(MKNetworkOperation *operation) {
        NSDictionary* response = operation.responseJSON;
        
        NSNumber* errorCode = response[@"error_code"];
        
        if (!errorCode || [errorCode integerValue] != 0)
        {
            completionBlock([NSError errorWithDescription:@"Failed to register new user."]);
        }
        else
        {
            completionBlock(nil);
        }
    } errorHandler:^(MKNetworkOperation *errorOp, NSError* error) {
        completionBlock(error);
    }];
    
    [self enqueueOperation:op];
}

- (void)loginWithEmail:(NSString *)email
          withPassword:(NSString *)password
       completionBlock:(NetworkEngineCompletionBlock)completionBlock
{
    MKNetworkOperation *op = [self operationWithPath:@"oauth/token"
                                              params:@{@"client_id" : CTF_API_CLIENT_ID, @"client_secret" : CTF_API_CLIENT_SECRET, @"grant_type" : CTF_API_GRANT_TYPE, @"username" : email, @"password" : password}
                                          httpMethod:@"POST"
                                                 ssl:NO];
    
    
    [op addCompletionHandler:^(MKNetworkOperation *operation) {
        NSDictionary* response = operation.responseJSON;
        NSString *error = response[@"error"];
        self.token = response[@"access_token"];
        if (error!=nil ||  _token == nil)
        {
            completionBlock([NSError errorWithDescription:@"Failed to log in."]);
        }
        else
        {
            completionBlock(_token);
            
        }
    } errorHandler:^(MKNetworkOperation *errorOp, NSError* error) {
        completionBlock(error);
    } ];
    
    [self enqueueOperation:op];

}

- (void)logout:(NetworkEngineCompletionBlock)completionBlock
{
    //There is no api at the moment, so this method can simply call completion block.
    completionBlock(nil);
}

- (void)createNewGame: (CTFGame *) game
   completionBlock:(NetworkEngineCompletionBlock)completionBlock;
{
   
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"dd/MM/yyyy hh:mma:ss"];
   NSString *dateString = [dateFormat stringFromDate:game.timeStart];
    
    MKNetworkOperation *op = [self operationWithPath:@"/api/secured/games"
                                              params:@{@"name" : game.name, @"description" : game.gameDescription, @"time_start" : dateString, @"duration" : game.duration, @"points_max" : game.pointsMax, @"players_max" : game.playersMax, @"localization" :@{@"name" : game.localizationName, @"latLng" :@{@"lat" : game.localizationLat, @"lng" : game.localizationLng},  @"radius" : game.localizationRadius}}
                                          httpMethod:@"POST"
                                                 ssl:NO];

    [op addHeaders:@{@"Accept" : @"application/json", @"Content-type" : @"application/json", @"Authorization" : @[@"Bearer %@", _token]}];
    [op setPostDataEncoding:MKNKPostDataEncodingTypeJSON];
    [op addCompletionHandler:^(MKNetworkOperation *operation) {
        NSDictionary* response = operation.responseJSON;
        NSString *error = response[@"error"];
        if (error==nil)
        {
            completionBlock(nil);
        }
        else
        {
            completionBlock([NSError errorWithDescription:@"Failed to create a new game."]);
        }
            
    } errorHandler:^(MKNetworkOperation *errorOp, NSError* error) {
        completionBlock(error);
    } ];
    
   [self enqueueOperation:op];
 
}

@end 
