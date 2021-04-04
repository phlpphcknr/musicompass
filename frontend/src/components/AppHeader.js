import styled from 'styled-components/macro'

export default function AppHeader() {
  return (
    <Header>
      <h1>musiCompass</h1>
      <p>...discover new sounds!</p>
    </Header>
  )
}

const Header = styled.header`
  background: var(--primary-color);
  box-shadow: 0px 2px 8px #333;
  padding: 10px 0px 20px 0px;
  font-family: 'Orange Juice';
  
  h1 {
    margin: 0px;
    padding: 8px;
    text-align: center;
    font-size: 1.6em;
    color: #636e72;
  }
  p {
    font-weight: bold;
    margin: 0px;
    text-align: center;
    font-size: 0.6em;
    color: black;
  }
`
