<script>
// @ts-nocheck

	import axios from "axios";
    import Navbar from "../../components/Navbar.svelte";
	import { onMount } from 'svelte';
    axios.defaults.baseURL = 'http://localhost:8080';
    import { Popover, Button } from 'flowbite-svelte'
    import { slide } from 'svelte/transition';
    import { Table, TableBody, TableBodyCell, TableBodyRow, TableHead, TableHeadCell } from 'flowbite-svelte';

    /**
	 * @type {any[]}
	 */
    let drivers = [];
    let firstName = '';
    let lastName = '';
    let phoneNumber = '';
    let email = '';
    let showForm = false;

    async function getDrivers(){
        try{
            const response = await axios.get('/driver');
            drivers = response.data;
        }catch(error){
            console.error(error);
        }
    }

    async function addNewDriver(){
        try{
            const response = await axios.post('/driver', {
                firstName, 
                lastName, 
                phoneNumber, 
                email
            });
            getDrivers();
            firstName = '';
            lastName = '';
            phoneNumber = '';
            email = '';
            showForm = false;
        }catch(error){
            console.error(error);
        }
    }

    async function deleteDriver(driverId){
      try{
        await axios.delete(`/driver/${driverId}`);
        drivers = drivers.filter(driver => driver.id !== driverId);
        getDrivers();
      }catch(error){
        console.error(error);
      }
    }

    async function updateDriver(driver){
      try{
        const {driverId, firstName, lastName, phoneNumber, email} = driver;
        await axios.put(`/driver/${driver.driverId}`, {driver, firstName: driver.firstName, lastName: driver.lastName, phoneNumber: driver.phoneNumber, email: driver.email});
        driver.isEditing = false;
      }catch(error){
        console.error(error);
      }
    }

    onMount(() => {
        getDrivers();
    })
</script>

<Navbar />
<div class="w-2/3 mx-auto bg-gray-300 rounded-lg">
  <Table striped={true}>
    <TableHead>
      <TableHeadCell>First Name</TableHeadCell>
      <TableHeadCell>Last Name</TableHeadCell>
      <TableHeadCell>Phone Number</TableHeadCell>
      <TableHeadCell>Email</TableHeadCell>
    </TableHead>
      <TableBody class="divide-y">
        {#each drivers as driver}
          <TableBodyRow>
            {#if driver.isEditing}
              <TableBodyCell>
                <input type="text" class="border border-gray-300 px-2 py-1 rounded" bind:value={driver.firstName} />
                <input type="text" class="border border-gray-300 px-2 py-1 rounded" bind:value={driver.lastName} />
                <input type="text" class="border border-gray-300 px-2 py-1 rounded" bind:value={driver.phoneNumber} />
                <input type="text" class="border border-gray-300 px-2 py-1 rounded" bind:value={driver.email} />
              </TableBodyCell>
              <TableBodyCell>
                <div class="flex justify-end">
                  <Button color = "green" style="margin-right: 0.5rem" on:click={() => { updateDriver(driver); driver.isEditing = false; }}>Save</Button>
                </div>
              </TableBodyCell>
            {:else}
              <TableBodyCell>{driver.firstName}</TableBodyCell>
              <TableBodyCell>{driver.lastName}</TableBodyCell>
              <TableBodyCell>{driver.phoneNumber}</TableBodyCell>
              <TableBodyCell>{driver.email}</TableBodyCell>
              <TableBodyCell>
                <div class = "flex justify-end">
                  <Button color = "blue" style="margin-right: 0.5rem;"on:click = {() => { driver.isEditing = true; }}>Edit</Button>
                  <Button color="red" style="margin-right: 0.5rem;" on:click={() => deleteDriver(driver.driverId)}>Delete</Button>
                </div>
              </TableBodyCell>
            {/if}
            </TableBodyRow>
          {/each}
        </TableBody>
      </Table>
</div>

<div class="text-center mt-4">
  <Button color="green" on:click={() => showForm = true}>Add New Driver</Button>
  <Popover class="w-64 text-sm font-light" title="New Driver" transition={slide}>
    {#if showForm}
    <form on:submit|preventDefault={addNewDriver}>
      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" bind:value={firstName} required />

      <label for="lastName">LastName:</label>
      <input type="text" id="lastName" bind:value={lastName} required />

      <label for="phoneNumber">Phone Number:</label>
      <input type="tel" id="phoneNumber" bind:value={phoneNumber} required />

      <label for="email">Email:</label>
      <input type="email" id="email" bind:value={email} required />

      <Button color="green" style="margin-right: 0.5rem; margin-top: 0.5rem; padding: 0.5rem 1rem; font-size: 0.8rem" on:click={addNewDriver}>Save</Button>
    </form>
    {/if}
  </Popover>
</div>