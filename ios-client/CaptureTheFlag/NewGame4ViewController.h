//
//  NewGame4ViewController.h
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 21.06.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface NewGame4ViewController : UIViewController <UIPickerViewDataSource,UIPickerViewDelegate> {
}

@property (strong, nonatomic) CTFGame *game;

@end