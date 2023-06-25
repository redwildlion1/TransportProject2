<script>
  import axios from "axios";
  import { onMount } from 'svelte';

  /**
   * @type {any[]}
   */
  let transports = [];

  /**
   * @type {any[]}
   */
  let drivers = [];

  /**
   * @type {any[]}
   */
  let locations = [];

  async function getTransports() {
    try {
      const response = await axios.get('http://localhost:8080/transport');
      transports = response.data;
    } catch (error) {
      console.error(error);
    }
  }

  async function getDriversName() {
    try {
      const response = await axios.get('http://localhost:8080/driver');
      drivers = response.data;
    } catch (error) {
      console.error(error);
    }
  }

  async function getLocationsAddress() {
    try {
      const response = await axios.get('http://localhost:8080/location');
      locations = response.data;
    } catch (error) {
      console.error(error);
    }
  }

  onMount(async () => {
    await getTransports();
    await getDriversName();
    await getLocationsAddress();
  })
</script>

<div class="container mx-auto px-4">
  <h1 class="text-4xl font-bold mb-8">Most recent transports</h1>
  <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
    {#each transports as transport}
    <div class="bg-white rounded-md shadow-lg border border-gray-300 p-6">
      <h2 class="text-lg font-bold mb-4">Transport #{transport.transportId}</h2>
      <p><span class="font-bold">Driver:</span> {drivers.find(d => d.driverId === transport.driverId)?.firstName} {drivers.find(d => d.driverId === transport.driverId)?.lastName}</p>
      <p><span class="font-bold">Location:</span> {locations.find(l => l.locationId === transport.locationId)?.address}</p>
      <p><span class="font-bold">Status:</span> {transport.status}</p>
      <p><span class="font-bold">Dispatch Date:</span> {transport.dispatchDate}</p>
      <p><span class="font-bold">Delivery Date:</span> {transport.deliveryDate}</p>
    </div>
    {/each}
  </div>
</div>
