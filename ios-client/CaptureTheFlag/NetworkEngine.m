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

- (void)createNewGame:(NSObject *)CTFGame
{
    MKNetworkOperation *op = [self operationWithPath:@"/api/secured/games"
                                              params:@{@"CTFGame" : CTFGame}
                                          httpMethod:@"POST"
                                                 ssl:NO];
    
    [op addCompletionHandler:^(MKNetworkOperation *operation) {
        NSDictionary* response = operation.responseJSON;
        NSString *error = response[@"error"];
        if (error==nil)
        {
            [ShowInformation showMessage:@"You've created a new game." withTitle:@"Success"];
        }
        else
        {
            //Chciałam to wpisać continue, tak jak to się robi w C czy C#, ale wyskakuje błąd, że nie można.
        }
            
    } errorHandler:^(MKNetworkOperation *errorOp, NSError* error) {
        [ShowInformation showError:@"Failed to create a new game. Make sure that all game parameters are correct and try again."];
    } ];
    
   [self enqueueOperation:op];
 
}

@end
