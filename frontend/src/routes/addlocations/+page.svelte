<script>
// @ts-nocheck

	import axios from 'axios';
	import { onMount } from 'svelte';
	axios.defaults.baseURL = 'http://localhost:8080';
	import Navbar from '../../components/Navbar.svelte';
	import { Popover, Button } from 'flowbite-svelte'
    import { slide } from 'svelte/transition';
    import { Table, TableBody, TableBodyCell, TableBodyRow, TableHead, TableHeadCell } from 'flowbite-svelte';

	/**
	 * @type {any[]}
	 */
	let locations = [];
	let address = '';
	let newLocationAddress = '';
	/**
	 * @type {any[]}
	 */
	let gates = [];
	let showForm = false;

	async function getLocations(){
		try{
			const response = await axios.get('/location');
			locations = response.data;
		}catch(error){
			console.error(error);
		}
	}

	/**
	 * @param {any} locationId
	 */
	async function deleteLocation(locationId){
		try{
			await axios.delete(`/location/${locationId}`);
			locations = locations.filter(location => location.id !== locationId);
			getLocations();
		}catch(error){
			console.error(error);
		}
	}

	async function addNewLocation(){
		try{
			const response = await axios.post('/location', {address: newLocationAddress});
			newLocationAddress = '';
			getLocations();
		}catch(error){
			console.error(error);
		}
	}
	
	/**
	 * @param {{ locationId: any; address: any; isEditing: boolean; }} location
	 */
	async function updateLocation(location) {
		try {
			const {locationId, address} = location;
			await axios.put(`/location/${location.locationId}`, {location, address: location.address });
			location.isEditing = false;
		} catch (error) {
			console.error(error);
		}
	}

	/**
	 * 
	 * @param {any[]} locationId
	 */
	async function getGates(locationId){
		try{
			const response = await axios.get(`/gate/location/${locationId}`);
			gates = response.data;
		}catch(error){
			console.error(error);
		}
	}

	/**
	 * @type {{ address?: any; locationId?: any[]; } | null}
	 */
	let selectedLocation = null;

  /**
	 * @param {{ locationId: any[]; }} location
	 */
  	function viewGates(location) {
    	selectedLocation = location;
    	getGates(location.locationId);
  	}

	/**
	 * @param {any} locationId
	 */
	async function addGate(locationId){
		try{
			const response = await axios.post('/gate', { locationId });
			const newGate = response.data;
			gates.push(newGate);
		}catch(error){
			console.error(error);
		}
	}

	onMount(() => {
		getLocations();
	});
</script>

<Navbar />
<div class="w-2/3 mx-auto bg-gray-300 rounded-lg">
	<Table striped={true}>
	  <TableHead>
		<TableHeadCell colspan={2}>Addresses</TableHeadCell>
	  </TableHead>
	  <TableBody class="divide-y">
		{#each locations as location}
		  <TableBodyRow>
			{#if location.isEditing}
			  <TableBodyCell>
				<input type="text" class="border border-gray-300 px-2 py-1 rounded" bind:value={location.address} />
			  </TableBodyCell>
			  <TableBodyCell>
				<div class="flex justify-end">
				  <Button color = "green" style="margin-right: 0.5rem" on:click={() => { updateLocation(location); location.isEditing = false; }}>Save</Button>
				</div>
			  </TableBodyCell>
			{:else}
			  <TableBodyCell>{location.address}</TableBodyCell>
			  <TableBodyCell>
				<div class="flex justify-end">
					<Button color="purple" style="margin-right: 0.5rem;" on:click={() => viewGates(location)}>View Gates</Button>
					<Button color="green" style="margin-right: 0.5rem;" on:click={() => addGate(location.locationId)}>Add Gate</Button>
					<Button color="blue" style="margin-right: 0.5rem;" on:click={() => { location.isEditing = true; }}>Edit</Button>
					<Button color="red" style="margin-right: 0.5rem;" on:click={() => {
						deleteLocation(location.locationId);
					}}>Delete</Button>
				  </div>
			  </TableBodyCell>
			{/if}
		  </TableBodyRow>
		{/each}
	  </TableBody>
	</Table>
  </div>
  

{#if selectedLocation !== null}
<div class="w-2/3 mx-auto mt-4">
  <h2>Gates for: "{selectedLocation.address}"</h2>
  {#each gates as gate}
  <div>{gate.gateId}</div>
  {/each}
</div>
{/if}

<div class="text-center mt-4">
    <Button color="green" on:click={() => showForm = true}>Add New Location</Button>
    <Popover class="w-64 text-sm font-light" title="New Address" transition={slide}>
        {#if showForm}
        <form on:submit|preventDefault={addNewLocation}>
            <label for="address">Enter the new address:</label>
            <input type="text" id="address" bind:value={newLocationAddress} required>
            
			<Button color="green" style="margin-right: 0.5rem; margin-top: 0.5rem; padding: 0.5rem 1rem; font-size: 0.8rem" on:click={addNewLocation}>Save</Button>
        </form>
    {/if}
    </Popover>
</div>