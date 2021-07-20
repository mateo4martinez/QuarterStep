# QuarterStep

Original App Design Project - README Template
===

# QuarterStep

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
In my app, a user should be able to create/play a short song and share it in a post that will be on a viewable feed. I also plan on the user being able to save songs.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Music/Social
- **Mobile:** Mobile first experience
- **Story:** Allows users to share short music pieces with short descriptions/tags and captions
- **Market:** Anyone that wants to create short basic melodies to show to their friends
- **Habit:** Users can post, create, and save songs whenever they want and quite frequently too due to the user friendly interface.
- **Scope:** I wanted this to be a niche app that anyone could use to begin learning music/experiment with it.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can create a song
* User can share a song
* User can play a song
* User can login/sign up
* User can view a feed of songs

**Optional Nice-to-have Stories**

* User can edit a song
* User can save a song
* User can delete a song
* User can like/comment on a post
* user can put tags on the song

### 2. Screen Archetypes

* Login/Sign up
   * User can login/sign up
* Feed
   * User can view a feed of songs
* Create a song
    * User can create a song
    * User can play a song
* Share a song
    * User can share a song

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Arrangement View (Create a song)
* Feed View
* Profile View
* Share View
* Saved Songs View

**Flow Navigation** (Screen to Screen)
* Arrangement View
    * Profile View
   * Share View
   * Saved songs view
   * Share View
* Feed View
   * Profile View
   * Arrangement View
* Profile View
    * Feed View
    * Arrangement View
* Share View
    * Feed View
* Save View
    * Share View

## Wireframes
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>![](https://i.imgur.com/CEAkt4O.jpg)

## Schema 
[This section will be completed in Unit 9]
### Models

* Post
    * ObjectID
        * String
        * Unique id for user post
    * artist
        * pointer to user
        * song creator
    * piece
        * File
        * song that user posts
    * caption
        * String
        * caption by user
    * tags
        * Array of Strings
        * Tags for the song
    * createdAt
        * DateTime
        * Date when post is created

* Song
    * Artist
        * Pointer to user
        * song creator
    * Piece
        * File
        * song that user posts
    * isFavorite
        * Boolean
        * Determines if song is a user favorite


### Networking
- List of network requests by screen
* Home Feed Screen
    * (Read/GET) Query all posts where user is author
    * (Create/POST) Create a new like on a post
    * (Delete) Delete existing like
    * (Create/POST) Create a new comment on a post
    * (Delete) Delete existing comment
* Share Screen
    * (Create/POST) Create a new post object
* Profile Screen
    * (Read/GET) Query logged in user object
    * (Update/PUT) Update user profile image
