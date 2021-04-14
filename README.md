<h2><p align=center>musiCompass</p></h3>
<h3><p align=center>... discover new sounds !</p></h3>
<br/>
The world of music often seems infinite like the universe itself. We often catch a name here or there or just a genre we are interested in and then it it is often difficult to find a proper release to start listening. 

**musiCompass** was created to give you some guidance. You can request an artist by providing criteria (gerne, role & gender) or directly enter a musician's or band's name to get their most popular/wanted releases. 
You are invited to recommend your favourite artists with tags to provide them as recommendations to other users.
So enjoy digging in those virtual crates and open your mind to sounds which were unknown to you before!

The data which is displayed is taken from the [Discogs API](https://www.discogs.com/developers). Each release gets a rating which is calculated from the number of Discogs users who have this release in their collection and the number of discogs users who have this release in their wantlist.

### Used technologies

- the frontend is written in *React.js*
- the backend is written in *Java* using *Spring* and *Maven*
- data which has been requested from the Discogs API is saved in a *MongoDB* to minimize loading times

#### You are invited to join

Clone the git repository and install the frontend via `npm`:

```
git clone git@github.com:phlpphcknr/musicompass.git
cd musicompass/frontend
npm i
```

To run the development server use:

```
npm start
```

To install the backend use `maven` and launch a local development server with the help of `spring`.

Furthermore you will need as environment variables:
- Discogs Token : can be generated within your [discogs](https://www.discogs.com/) account 

Keep on coding! 
