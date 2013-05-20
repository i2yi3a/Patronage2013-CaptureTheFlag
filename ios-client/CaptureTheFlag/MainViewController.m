//
//  MainViewController.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 14.04.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "MainViewController.h"
#import "MainTableViewCell.h"
#import "NewGameViewController.h"
#import "GamesViewController.h"
#import "MyGamesViewController.h"

@interface MainViewController ()

@end

@implementation MainViewController

@synthesize mainViewControllerCellLabels = _mainViewControllerCellLabels;
@synthesize mainViewControllerCellImages = _mainViewControllerCellImages;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.mainViewControllerCellLabels = [[NSArray alloc]
                     initWithObjects:@"new game",
                     @"games",
                     @"my games",
                     @"profile",
                     @"logout", nil];
    self.mainViewControllerCellImages = [[NSArray alloc]
                      initWithObjects:@"BLstream.png",
                      @"BLstream.png",
                      @"BLstream.png",
                      @"BLstream.png",
                      @"BLstream.png", nil];
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == 0)
    {
       [self performSegueWithIdentifier:@"segueToNewGame" sender:self];
    }
    if (indexPath.row == 1)
    {
        [self performSegueWithIdentifier:@"segueToGames" sender:self];
    }
    if (indexPath.row == 2)
    {
        [self performSegueWithIdentifier:@"segueToMyGames" sender:self];
        
    }
    if (indexPath.row == 3)
    {
      [tableView beginUpdates];
      [tableView endUpdates];
        //[self performSegueWithIdentifier:@"segueToProfile" sender:self];
    }
    if (indexPath.row == 4)
    {
        [self performSegueWithIdentifier:@"segueLogout" sender:self];
    }

}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
        {
            if ([[segue identifier] isEqualToString:@"segueToNewGame"])
            {
                
            }
            if ([[segue identifier] isEqualToString:@"segueToGames"])
            {
                
            }
            if ([[segue identifier] isEqualToString:@"segueToMyGames"])
            {
                
            }
            if ([[segue identifier] isEqualToString:@"segueToProfile"])
            {

            }
            if ([[segue identifier] isEqualToString:@"segueLogout"])
            {
                
            }

        }


#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.mainViewControllerCellLabels count];
}

/*- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    // Configure the cell...
    
    return cell;
}
*/
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"MainViewCell";
    
    MainTableViewCell *cell = [tableView
                              dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[MainTableViewCell alloc]
                initWithStyle:UITableViewCellStyleDefault
                reuseIdentifier:CellIdentifier];
    }
    
    // Configure the cell...
    cell.mainViewControllerCellLabel.text = [self.mainViewControllerCellLabels
                           objectAtIndex: [indexPath row]];
    
    UIImage *menuImage = [UIImage imageNamed:
                         [self.mainViewControllerCellImages objectAtIndex: [indexPath row]]];
    
    cell.mainViewControllerCellImage.image = menuImage;
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if ( indexPath.row == 0) {
        return 85.0f;
    }
    if ( indexPath.row == [tableView indexPathForSelectedRow].row) {
        return 170.0f;
    }
    
    return 85.0f;
}
/*
 // Override to support conditional editing of the table view.
 - (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
 {
 // Return NO if you do not want the specified item to be editable.
 return YES;
 }
 */

/*
 // Override to support editing the table view.
 - (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
 {
 if (editingStyle == UITableViewCellEditingStyleDelete) {
 // Delete the row from the data source
 [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
 }
 else if (editingStyle == UITableViewCellEditingStyleInsert) {
 // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
 }
 }
 */

/*
 // Override to support rearranging the table view.
 - (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
 {
 }
 */

/*
 // Override to support conditional rearranging of the table view.
 - (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
 {
 // Return NO if you do not want the item to be re-orderable.
 return YES;
 }
 */

#pragma mark - Table view delegate

/*- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
 
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
 
}
*/
@end
