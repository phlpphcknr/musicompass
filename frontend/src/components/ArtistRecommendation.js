import styled from 'styled-components/macro'

export default function ArtistRecommendation ({onRecommend, artistName}){

    const recommend = () => {};

    return(
    <ArtistRecommender>
        <button onClick={recommend} >RECOMMEND</button>
    </ArtistRecommender>
    )
}

const ArtistRecommender = styled.section`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  margin: 0px 20px;
  background: var(--primary-color);
  box-shadow: 0px 2px 4px #333;
`