//
//  NetworkEngine.h
//  CaptureTheFlag
//
//  Created by Sebastian Jędruszkiewicz on 4/4/13.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "MKNetworkEngine.h"
#import "CTFGame.h"

//object isClassOf NSError => error
//else NSDictionary - JSON representation of response
typedef void (^NetworkEngineCompletionBlock)(NSObject* object);

@interface NetworkEngine : MKNetworkEngine

+ (NetworkEngine *)getInstance;

- (void)registerUser:(NSString *)email
        withPassword:(NSString *)password
     completionBlock:(NetworkEngineCompletionBlock)completionBlock;

- (void)loginWithEmail:(NSString *)email
          withPassword:(NSString *)password
       completionBlock:(NetworkEngineCompletionBlock)completionBlock;

- (void)logout:(NetworkEngineCompletionBlock)completionBlock;

- (void)createNewGame:(CTFGame *) game
      completionBlock:(NetworkEngineCompletionBlock)completionBlock;

-(NSError *)errorFromServerErrorCode:(NSInteger)code;

- (CTFGame *)gameFromDictionary:(NSDictionary *)response;

-(void)getGameDetails: (NSString *)gameId
      completionBlock:(NetworkEngineCompletionBlock)completionBlock;

-(void)getGames: (CLLocation *)location
           name: (NSString *)name
         status: (NSString *)status
        myGames: (BOOL)myGames
completionBlock: (NetworkEngineCompletionBlock)completionBlock;


@end
