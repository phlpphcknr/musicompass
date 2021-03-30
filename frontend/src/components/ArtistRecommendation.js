import styled from 'styled-components/macro'

export default function ArtistRecommendation ({onRecommend, artistName}){

    const recommend = () => {};

    return(
    <ArtistRecommender>
        <h3>Recommend '{artistName}' to other users</h3>
        <button onClick={recommend} >RECOMMEND</button>
    </ArtistRecommender>
    )
}

const ArtistRecommender = styled.section`
`