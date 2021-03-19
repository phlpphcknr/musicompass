import styled from 'styled-components/macro'

export default function ArtistHeader({ artist }) {
  return (
    <Header>
      <img src={artist.artistImageUrl} alt={'Could not be loaded'} />
      <h3>{artist.artistName}</h3>
    </Header>
  )
}

const Header = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0px 20px 20px;
  background: var(--primary-color);
  width: 90%;
  box-shadow: 0px 2px 4px #333;
  img {
    width: 50%;
    max-width: 120px;
  }
  h3 {
    padding: 0px;
    margin: 0px;
    width: 100%;
    text-align: center;
    font-size: 1.6em;
    font-family: 'Orange Juice';
    color: #636e72;
  }
`
