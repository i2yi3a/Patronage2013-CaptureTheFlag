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
@property (nonatomic, strong) NSNumber* gameID;


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
        NSInteger code=[errorCode integerValue];
        
        
        if (!errorCode || [errorCode integerValue] != 0)
        {
            //completionBlock([NSError errorWithDescription:@"Failed to register new user."]);
            NSError *error=[self errorFromServerErrorCode:code];
            [ShowInformation showError:error.localizedDescription];
            
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
        
        NSNumber* errorCode = response[@"error_code"];
        NSInteger code=[errorCode integerValue];
        
        if (error!=nil ||  _token == nil)
        {
            //completionBlock([NSError errorWithDescription:@"Failed to log in."]);
            NSError *error=[self errorFromServerErrorCode:code];
            [ShowInformation showError:error.localizedDescription];
            
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
    self.token = nil;
    //There is no api at the moment, so this method can simply call completion block.
    completionBlock(nil);
}

- (void)createNewGame: (CTFGame *) game
      completionBlock:(NetworkEngineCompletionBlock)completionBlock
{
    
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"dd/MM/yyyy hh:mma:ss"];
    NSString *dateString = [dateFormat stringFromDate:game.timeStart];
    
    MKNetworkOperation *op = [self operationWithPath:@"/api/secured/games"
                                              params:@{@"name" : game.name, @"description" : game.gameDescription, @"time_start" : dateString, @"duration" : game.duration, @"points_max" : game.pointsMax, @"players_max" : game.playersMax, @"localization" :@{@"name" : game.localizationName, @"latLng" :@[[NSNumber numberWithFloat: game.localization.coordinate.latitude], [NSNumber numberWithFloat: game.localization.coordinate.longitude]],  @"radius" : game.localizationRadius}, @"red_team_base" : @{@"name" :game.redTeamBaseName, @"latLng" : @[[NSNumber numberWithFloat:game.redTeamBaseLocalization.coordinate.latitude], [NSNumber numberWithFloat: game.redTeamBaseLocalization.coordinate.longitude]]}, @"blue_team_base" : @{@"name" : game.blueTeamBaseName, @"latLng" : @[[NSNumber numberWithFloat: game.blueTeamBaseLocalization.coordinate.latitude], [NSNumber numberWithFloat: game.blueTeamBaseLocalization.coordinate.longitude]]}}
                                          httpMethod:@"POST"
                                                 ssl:NO];
    
    [op addHeaders:@{@"Accept" : @"application/json", @"Content-type" : @"application/json", @"Authorization" : @[@"Bearer %@", _token]}];
    [op setPostDataEncoding:MKNKPostDataEncodingTypeJSON];
    [op addCompletionHandler:^(MKNetworkOperation *operation) {
        NSDictionary* response = operation.responseJSON;
        NSString *error = response[@"error"];
        self.gameID = response[@"id"];

        
        NSNumber* errorCode = response[@"error_code"];
        NSInteger code=[errorCode integerValue];
        
        if (error==nil)
        {
            completionBlock(nil);
        }
        else
        {
            //completionBlock([NSError errorWithDescription:@"Failed to create a new game."]);
            NSError *error=[self errorFromServerErrorCode:code];
            [ShowInformation showError:error.localizedDescription];
            
        }
        
    } errorHandler:^(MKNetworkOperation *errorOp, NSError* error) {
        completionBlock(error);
    } ];
    
    [self enqueueOperation:op];
    
}

-(NSError *)errorFromServerErrorCode:(NSInteger)code
{
    NSString *msg;
    switch (code) {
        case 0: msg=NSLocalizedString(@"server.success", nil);
        case 1: msg=NSLocalizedString(@"server.error.bad.request", nil);
        case 2: msg=NSLocalizedString(@"server.error.internal.errort", nil);
        case 3: msg=NSLocalizedString(@"server.error.resource.not.found", nil);
        case 4: msg=NSLocalizedString(@"server.error.resource.already.exists", nil);
        case 5: msg=NSLocalizedString(@"server.error.resource.cannot.be.delated", nil);
        case 100: msg=NSLocalizedString(@"server.error.cannot.create.new.player", nil);
        case 101: msg=NSLocalizedString(@"server.error.player.already.exists", nil);
        default: msg=NSLocalizedString(@"server.error.general", nil);
    }
    return [NSError errorWithDescription:msg];
}

-(void)getGameDeatails: (NetworkEngineCompletionBlock)completionBlock
{
    MKNetworkOperation *op=[self operationWithPath:@"/api/secured/games/" params: @{@"id" : _gameID}  httpMethod:@"GET" ssl:NO];
    
    [op addHeaders:@{@"Accept" : @"application/json", @"Content-type" : @"application/json", @"Authorization" : @[@"Bearer %@", _token]}];
    
    [op addCompletionHandler:^(MKNetworkOperation *operation){
        
        NSDictionary* response = operation.responseJSON;
        NSArray* games = response[@"games"];
        NSMutableArray* a;
        
        for (NSDictionary *g in games)
        {
            CTFGame *game = [[CTFGame alloc] init];
            game.name = g[@"name"];
            game.gameDescription = g[@"description"];
            game.timeStart = g[@"time_start"];
            game.duration = g[@"duration"];
            game.pointsMax = g[@"points_max"];
            game.playersMax = g[@"players_max"];
            game.localizationName = g[@[@"localization" "name"]];
            game.localization = g[@[@"localization" "latLng"]];
            game.localizationRadius = g[@[@"localization" "name"]];
            game.redTeamBaseName = g[@[@"red_team_base" "name"]];
            game.redTeamBaseLocalization = g[@[@"red_team_base" "latLng"]];
            game.blueTeamBaseName = g[@[@"blue_team_base" "name"]];
            game.blueTeamBaseLocalization = g[@[@"blue_team_base" "latLng"]];
            [a addObject:game];
            
        }
        
        completionBlock(a);
        
    } errorHandler:^(MKNetworkOperation *completedOperation, NSError *error) {
        //
    }];
    
}



@end 
