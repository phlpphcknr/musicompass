import { useParams } from 'react-router-dom'

export default function ArtistOverview() {
  const { artistName } = useParams()

  return <section>Welcome to the page of {artistName}</section>
}
