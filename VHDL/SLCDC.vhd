----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    21:12:03 06/03/2020 
-- Design Name: 
-- Module Name:    SLCDC - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity SLCDC is
end SLCDC;

architecture Structural of SLCDC is
	COMPONENT SerialReceiver
		Port ( 
			  CLK : in STD_LOGIC;
			  RST : in STD_LOGIC;
	        SDX : in  STD_LOGIC;
           SCLK : in  STD_LOGIC;
           SS : in  STD_LOGIC;
           accept : in  STD_LOGIC;
           D : out  STD_LOGIC_VECTOR (4 downto 0);
           DXval : out  STD_LOGIC);
	END COMPONENT;
begin
	
end Structural;

