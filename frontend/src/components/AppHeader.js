import styled from 'styled-components/macro'

export default function AppHeader() {
  return (
    <Header>
      <h1>musiCompass</h1>
      <h5>...discover new sounds!</h5>
    </Header>
  )
}

const Header = styled.header`
  background: var(--primary-color);
  box-shadow: 0px 2px 4px #333;
  h1 {
    padding: 8px;
    margin: 0px;
    text-align: center;
    font-size: 1.6em;
    font-family: 'Orange Juice';
    color: #636e72;
  }
  h5 {
    padding: 8px;
    margin: 0px;
    text-align: center;
    font-size: 0.6em;
    font-family: 'Orange Juice';
    color: black;
  }
`
