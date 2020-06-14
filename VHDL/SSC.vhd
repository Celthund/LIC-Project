----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    15:21:38 06/14/2020 
-- Design Name: 
-- Module Name:    SSC - Behavioral 
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

entity SSC is
	Port ( 
		  CLK : in STD_LOGIC;
		  RST : in STD_LOGIC;
		  SDX : in  STD_LOGIC;
		  SCLK : in  STD_LOGIC;
		  SS : in  STD_LOGIC;
		  sid : out std_logic_vector(1 downto 0);
		  vol : out std_logic_vector(1 downto 0);
		  Play : out STD_LOGIC);
end SSC;

architecture Structural of SSC is
	COMPONENT SoundController 
	Port ( 
		CLK : in STD_LOGIC;
		RST : in STD_LOGIC;
		Dval : in STD_LOGIC;
      Din : in  std_logic_vector(3 downto 0);
      sid : out std_logic_vector(1 downto 0);
		vol : out std_logic_vector(1 downto 0);
		Play : out STD_LOGIC;
      done : out STD_LOGIC);
	END COMPONENT;
	COMPONENT SerialReceiverSound
		Port ( 
			  CLK : in STD_LOGIC;
			  RST : in STD_LOGIC;
	        SDX : in  STD_LOGIC;
           SCLK : in  STD_LOGIC;
           SS : in  STD_LOGIC;
           accept : in  STD_LOGIC;
           D : out  STD_LOGIC_VECTOR (3 downto 0);
           DXval : out  STD_LOGIC);
	END COMPONENT;
	SIGNAL DXValDVal, doneAccept : STD_LOGIC;
	SIGNAL dataInternal : std_logic_vector(3 downto 0);
begin
	SerialReceiverSoun : SerialReceiverSound
	PORT MAP ( 
			RST => RST,
			CLK => CLK,
			SDX => SDX,
			SCLK => SCLK,
			SS => SS,
			accept => doneAccept,
			D => dataInternal,
			DXval => DXValDVal
	 );
	SoundControll : SoundController
	PORT MAP ( 
			RST => RST,
			CLK => CLK,
			Dval => DXValDVal,
			Din => dataInternal,
			sid => sid,
			vol => vol,
			Play => Play,
			done => doneAccept
	 );

end Structural;

